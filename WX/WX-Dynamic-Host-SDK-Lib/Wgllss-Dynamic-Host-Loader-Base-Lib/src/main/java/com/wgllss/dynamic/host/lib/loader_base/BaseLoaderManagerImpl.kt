package com.wgllss.dynamic.host.lib.loader_base

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.CDLFD
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.CLMD
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.FIRST
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.HOME
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.MLK
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.VERSION
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.dldir
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.fstdir
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.versionFile
import com.wgllss.dynamic.host.lib.download.DynamicDownloadPlugin
import com.wgllss.dynamic.host.lib.download.IDynamicDownLoadFace
import com.wgllss.dynamic.host.lib.home.ILoadHome
import com.wgllss.dynamic.host.lib.loader.ILoaderManager
import com.wgllss.dynamic.host.lib.loader_base.DynamicManageUtils.getDlfn
import com.wgllss.dynamic.host.lib.runtime.ContainerClassLoader
import com.wgllss.dynamic.host.lib.runtime.InstalledApk
import com.wgllss.dynamic.host.lib.runtime.MultiDynamicRuntime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

open class BaseLoaderManagerImpl : ILoaderManager {

    /**总版本号*/
    protected var v = 1000

    /**宿主版本号*/
    protected var hostV = 1000

    /** class load first dex 首次加载 实现 dex first:启动页之后的第一个首页实现类 second:加载本地文件名称 third:版本号 **/
    protected lateinit var clfd: Triple<String, String, Int>

    /** 加载插件管理器 dex **/
    protected lateinit var clmd: Triple<String, String, Int>

    /** 下载接口实现 dex **/
    protected lateinit var cdlfd: Triple<String, String, Int>

    /** 必备的 （从启动到首页第一个界面） dex map集合 **/
    protected lateinit var mapDlu: MutableMap<String, Pair<String, Int>>

    /** 必备的 （从启动到首页第一个界面）之外的其他 dex map集合 **/
    protected lateinit var cotd: MutableMap<String, Int>

    protected lateinit var faceImpl: IDynamicDownLoadFace

    protected var mapOthers = mutableMapOf<String, String>()

    /** 是否显示启动加载页面标志 **/
    protected var isShowLoadFlag = false

    /** 代表前面的 lib dx 是否加载完成 ，只有加载完成了之后才能加载 others**/
    protected var libIsLoadComplete = false
    protected var firstLoadSuccess = false

    /** 是否有旧的文件需要删除*/
    protected var hasOldFileDelete = false
    protected var libLoadCount: Int = 0

    override fun getClfdImpl() = clfd

    override fun getClmdImpl() = clmd

    override fun getCdlfdImpl() = cdlfd

    override fun getMapDluImpl() = mapDlu

    override fun getCotdImpl() = cotd

    override fun hasOldFileNeedDelete() = hasOldFileDelete

    override fun getDownloadFace() = faceImpl

    override fun initDynamicLoader(
        context: Context,
        v: Int,
        clfd: Triple<String, String, Int>,
        clmd: Triple<String, String, Int>,
        cdlfd: Triple<String, String, Int>,
        mapDlu: MutableMap<String, Pair<String, Int>>,
        cotd: MutableMap<String, Int>,
        faceImpl: IDynamicDownLoadFace,
        isMustShowLoading: Boolean
    ) {
        this.v = v

        this.clfd = clfd
        this.clmd = clmd
        this.cdlfd = cdlfd

        this.mapDlu = mapDlu
        this.cotd = cotd
        this.faceImpl = faceImpl
        isShowLoadFlag = isMustShowLoading
        mapDlu.forEach { (key, value) ->
            initPlugin(context, key, getDlfn(value.first, value.second))
        }
        (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).runningAppProcesses?.onEach { p ->
            p.processName?.takeIf {
                p.pid == android.os.Process.myPid() && context.packageName == it
            }?.let {
                checkConfig(context)
            }
        }
    }

    override fun initDynamicRunTime(context: Context, contentKey: String, fileAbsolutePath: String) {
        val oDexPath = context.getDir("dex", Application.MODE_PRIVATE).absolutePath
        val installedApk = InstalledApk(fileAbsolutePath, oDexPath, null)
        MultiDynamicRuntime.loadContainerApk(contentKey, installedApk)
    }

    override fun initHomeCreate(context: Context, classLoader: ClassLoader) {
        libIsLoadComplete = true
        classLoader?.parent?.takeIf {
            it is ContainerClassLoader
        }?.run {
            (this as ContainerClassLoader).getInterface(ILoadHome::class.java, clfd.first).run {
                loadHomeCreate(context)
                mapOthers?.forEach {
                    initDynamicRunTime(context, it.key, it.value)
                }
            }
        }
    }

    private fun initPlugin(context: Context, key: String, fileName: String) {
        if (key == HOME) initPluginFirst(context, fileName) else initPluginLib(context, fileName)
    }

    /**
     * @author:Wgllss
     * 1:先检查是否有下载的启动页,有则加载
     * 2:再检查是否下载过主页，有则加载
     * 3:如果没有下载的主页，则加载默认load 页面，在展示load 页面是 下载所有插件
     * @param context:上下文
     * @param fileName:加载的首页本地文件名字
     */
    private fun initPluginFirst(context: Context, fileName: String) {
        DynamicManageUtils.getDxFile(context, dldir, getDlfn(clfd.second, clfd.third)).takeIf { it.exists() }?.run {
            initDynamicRunTime(context, MLK, absolutePath)
            libLoadCount++
            libIsLoadComplete = libLoadCount == mapDlu.size
            return
        }

        if (!isShowLoadFlag) {
            DynamicManageUtils.getDxFile(context, dldir, fileName).takeIf { it.exists() }?.run {
                initDynamicRunTime(context, MLK, absolutePath)
                firstLoadSuccess = true
                libLoadCount++
                libIsLoadComplete = libLoadCount == mapDlu.size
                return
            }
        }
        DynamicManageUtils.getDxFile(context, fstdir, getDlfn(clfd.second, clfd.third)).run {
            takeUnless { it.exists() }?.run {
                DynamicManageUtils.copyFileFromAssetsToSD(context, this, getDlfn(clfd.second, clfd.third))
            }
            initDynamicRunTime(context, MLK, absolutePath)
        }
    }

    private fun initPluginLib(context: Context, fileName: String) {
        if (isShowLoadFlag) return
        val file = DynamicManageUtils.getDxFile(context, dldir, fileName)
        if (file.exists()) {
            initDynamicRunTime(context, fileName, file.absolutePath)
            libLoadCount++
        } else {
            isShowLoadFlag = true
        }
    }


    /**
     * 检查配置
     */
    private fun checkConfig(context: Context) {
        GlobalScope.launch {
            try {
                val dynamicHelper = DynamicDownloadPlugin(faceImpl)
                var needDownloadOther = false
                cotd?.forEach { (key, v) ->
                    val file = DynamicManageUtils.getDxFile(context, dldir, "${key}_${v}")
                    if (file.exists()) {
                        if (!key.contains("_res"))
                            initDynamicRunTime(context, key, file.absolutePath)
                    } else {
                        needDownloadOther = true
                    }
                }
                val result = dynamicHelper.getPluginInfo()
                result?.run {
                    log("BaseLoaderManagerImpl.v  ${this@BaseLoaderManagerImpl.v} v:$v libIsLoadComplete:$libIsLoadComplete")
                    val isFirstLoad = this@BaseLoaderManagerImpl.v == 1000
                    if (isFirstLoad && this@BaseLoaderManagerImpl.v == v) {
                        /**  第一次加载第一版版 时 的 others  **/
                        othersList?.forEach {
                            it.run {
                                doOthers(context, this@launch, dynamicHelper, dlu, getDlfn(dlu, v), isApkRes, libIsLoadComplete) { }
                            }
                        }
                        return@launch
                    }

                    othersList?.forEach {
                        it.run {
                            v.takeIf { ver ->
                                !cotd.containsKey(dlu) || needDownloadOther || isFirstLoad || ver > cotd[dlu]!!
                            }?.run {
                                val isCondition = libIsLoadComplete && (isFirstLoad || needDownloadOther || firstLoadSuccess)
                                doOthers(context, this@launch, dynamicHelper, dlu, getDlfn(dlu, v), isApkRes, isCondition) {
                                    if (isFirstLoad || needDownloadOther) {
                                        cotd[it.dlu] = v
                                    }
                                }
                            }
                        }
                    }

                    takeIf { v > this@BaseLoaderManagerImpl.v }?.run {
                        async {
                            DynamicManageUtils.getDxFile(context, dldir, versionFile).takeIf { it.exists() }?.delete()
                            dynamicHelper.initDynamicByKey(context, VERSION, dldir, versionFile)
                        }
                        takeIf { clfd.v > this@BaseLoaderManagerImpl.clfd.third }?.run { async { dynamicHelper.initDynamicByKey(context, FIRST, dldir, getDlfn(clfd.dlu, clfd.v)) } }
                        takeIf { clmd.v > this@BaseLoaderManagerImpl.clmd.third }?.run { async { dynamicHelper.initDynamicByKey(context, CLMD, dldir, getDlfn(clmd.dlu, clmd.v)) } }
                        takeIf { cdlfd.v > this@BaseLoaderManagerImpl.cdlfd.third }?.run { async { dynamicHelper.initDynamicByKey(context, CDLFD, dldir, getDlfn(cdlfd.dlu, cdlfd.v)) } }
                        mapDl.forEach { (key, p) ->
                            p.takeIf { p.v > mapDlu[key]!!.second }?.run { async { dynamicHelper.initDynamicByKey(context, key, dldir, getDlfn(dlu, v)) } }
                        }
                    }
                    /** 当本地版本和 线上版本一样时候 就检测有没有久的可以删除的文件 **/
                    hasOldFileDelete = v == this@BaseLoaderManagerImpl.v
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 统一处理others 插件
     * @param context :上下文
     * @param scope :协程域
     * @param dynamicHelper :下载帮助工具类
     * @param dlu :下载地址
     * @param dlfn :下载本地文件名
     * @param isApkRes :是否是纯apk的res资源
     * @param dlfn :下载本地文件名
     * @param contentKey :runtime 装载的key
     * @param ifCondition :是否达到立即装载条件
     * @param apkElseBlock :是apk纯资源res Apk 处理方式
     */
    private fun doOthers(context: Context, scope: CoroutineScope, dynamicHelper: DynamicDownloadPlugin, dlu: String, dlfn: String, isApkRes: Boolean, ifCondition: Boolean, apkElseBlock: () -> Unit) {
        scope.async {
            dynamicHelper.initDynamicPlugin(context, dlu, dldir, dlfn).run {
                if (!isApkRes)
                    synchronized(firstLoadSuccess) {
                        if (ifCondition)
                            initDynamicRunTime(context, dlu, fileAbsolutePath)
                        else {
                            mapOthers[dlu] = fileAbsolutePath
                        }
                    }
                else
                    apkElseBlock.invoke()
            }
        }
    }

    private fun log(message: String) {
        if (faceImpl.isDebug())
            android.util.Log.e("BaseLoaderManagerImpl", message)
    }
}