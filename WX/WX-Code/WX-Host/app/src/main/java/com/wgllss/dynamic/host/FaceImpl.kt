package com.wgllss.dynamic.host

import android.text.TextUtils
import com.wgllss.core.units.DeviceIdUtil
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.CDLFD
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.CLMD
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.COMMON
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.COMMON_BUSINESS
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.FIRST
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.HOME
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.MANAGER
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.RESOURCE_SKIN
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.RUNTIME
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.VERSION
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.WEB_ASSETS
import com.wgllss.dynamic.host.lib.download.IDynamicDownLoadFace

class FaceImpl : IDynamicDownLoadFace {

    private var baseXL: String = ""

    override fun getHostL() = "http://192.168.3.108:8080/assets/WXDynamicPlugin/"
//    override fun getHostL() = "http://192.168.1.9:8080/assets/WXDynamicPlugin/"
    //todo 自己本地搭一个服务器，或者 自己服务器 或者 像我一样在gitee上面在自己的项目下建一个文件当作服务器 供下载,
    // 切记不要往往我的 gitee 项目上面推
//    override fun getHostL() = "https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/"

    /** 0:WXDynamicPlugin 动态化插件框架 理论上已经做到了可以完全不动宿主,但是如果一定要动宿主 可以提供以下思路:
     *  1:可以根据 宿主版本号得到 宿主版本支持的 的插件,
     *  2:当宿主必须 需要升级时,升级后原版本的插件不可用了，插件配置在新宿主版本文件夹下面，原宿主版本文件夹可可以先动态配置 在启动页 升级下载新的宿主
     *  @example  宿主版本 10000 版本支持的插件 放在服务端 WXDynamicPlugin/10000/ 文件夹下  20000版本的插件放在 WXDynamicPlugin/20000/下面
     */
    override fun getBaseL(): String {
        if (TextUtils.isEmpty(baseXL)) {
            baseXL = StringBuilder().append(getHostL()).append(DeviceIdUtil.getDeviceId()).append("/").append(BuildConfig.VERSION_CODE).append("/").toString()
        }
        return baseXL
    }

    override fun isDebug() = false

    override fun getOtherDLU() = realUrl("vc")

    override fun getMapDLU() = mutableMapOf(
        VERSION to realUrl("classes_version_dex"),
        COMMON to realUrl("classes_common_lib_dex"),
        WEB_ASSETS to realUrl("classes_business_web_res"),
        COMMON_BUSINESS to realUrl("classes_business_lib_dex"),
        HOME to realUrl("classes_home_dex"),
        RESOURCE_SKIN to realUrl("classes_common_skin_res"),
        RUNTIME to realUrl("classes_wgllss_dynamic_plugin_runtime"),
        MANAGER to realUrl("classes_manager_dex"),
        FIRST to realUrl("classes_loading_dex"),
        CLMD to realUrl("class_loader_impl_dex"),
        CDLFD to realUrl("classes_downloadface_impl_dex")
    )

    override fun getLoadVersionClassName() = "com.wgllss.loader.version.LoaderVersionImpl"

    private fun realUrl(name: String) = StringBuilder().append(getBaseL()).append(name).toString()

}