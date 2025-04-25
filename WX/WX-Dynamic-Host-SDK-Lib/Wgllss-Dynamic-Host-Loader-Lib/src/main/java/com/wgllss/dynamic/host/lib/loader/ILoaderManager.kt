package com.wgllss.dynamic.host.lib.loader

import android.content.Context
import com.wgllss.dynamic.host.lib.download.IDynamicDownLoadFace
import kotlinx.coroutines.Deferred

interface ILoaderManager {


    /**
     *@param context : 上下文
     *@param v : 总版本号
     *@param clfd : 首次引导页 dex
     *@param clmd : 加载插件管理器 dex
     *@param cdlfd : 下载接口实现 dex
     *@param mapDlu : app 必备的 （从启动到首页第一个界面） dex map集合
     *@param cotd : app 必备的 （从启动到首页第一个界面）之外的其他 dex map集合
     */
    fun initDynamicLoader(context: Context, v: Int, clfd: Triple<String, String, Int>, clmd: Triple<String, String, Int>, cdlfd: Triple<String, String, Int>, mapDlu: MutableMap<String, Pair<String, Int>>, cotd: MutableMap<String, Int>, faceImpl: IDynamicDownLoadFace, isMustShowLoading: Boolean)

    /**
     * class load first dex
     * 首次加载 实现 dex
     * first:启动页之后的第一个首页实现类
     * second:加载本地文件名称
     * third:版本号
     **/
    fun getClfdImpl(): Triple<String, String, Int>

    /**
     * class load manager  dex
     * 加载插件管理器 实现 dex
     * first:启动页之后的第一个首页实现类
     * second:加载本地文件名称
     * third:版本号
     **/
    fun getClmdImpl(): Triple<String, String, Int>

    /**
     * class download face dex
     * 下载接口实现 实现 dex
     * first:启动页之后的第一个首页实现类
     * second:加载本地文件名称
     * third:版本号
     **/
    fun getCdlfdImpl(): Triple<String, String, Int>

    /** 必备的 （从启动到首页第一个界面） dex map集合 **/
    fun getMapDluImpl(): MutableMap<String, Pair<String, Int>>

    /** 必备的 （从启动到首页第一个界面）之外的其他 dex map集合 **/
    fun getCotdImpl(): MutableMap<String, Int>

    /** 是否有旧的版本文件需要删除*/
    fun hasOldFileNeedDelete(): Boolean

    fun getDownloadFace(): IDynamicDownLoadFace

    fun initDynamicRunTime(context: Context, contentKey: String, fileAbsolutePath: String)

    fun initHomeCreate(context: Context, classLoader: ClassLoader)

    fun isFShowLoadFlag(): Boolean

    fun getOtherLoadStatus(): MutableMap<String, Deferred<Boolean>>

}