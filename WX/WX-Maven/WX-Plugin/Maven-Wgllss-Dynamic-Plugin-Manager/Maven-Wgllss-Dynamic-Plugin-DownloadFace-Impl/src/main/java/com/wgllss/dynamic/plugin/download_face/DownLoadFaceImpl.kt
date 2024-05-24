package com.wgllss.dynamic.plugin.download_face

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

class DownLoadFaceImpl : IDynamicDownLoadFace {

    private var baseXL: String = ""

    //    override fun getHostL() = "http://192.168.3.21:8080/assets/WXDynamicPlugin/"
//    override fun getHostL() = "http://192.168.1.9:8080/assets/WXDynamicPlugin/"
    override fun getHostL() = "https://gitee.com/wgllss888/WXDynamicPlugin/raw/master/WX-Resource/"

    /** 0:WXDynamicPlugin 动态化插件框架 理论上已经做到了可以完全不动宿主,但是如果一定要动宿主 可以提供以下思路:
     *  1:可以根据 宿主版本号得到 宿主版本支持的 的插件,
     *  2:当宿主必须 需要升级时,升级后原版本的插件不可用了，插件配置在新宿主版本文件夹下面，原宿主版本文件夹可可以先动态配置 在启动页 升级下载新的宿主
     *  @example  宿主版本 10000 版本支持的插件 放在服务端 WXDynamicPlugin/10000/ 文件夹下  20000版本的插件放在 WXDynamicPlugin/20000/下面
     */
    override fun getBaseL(): String {
        if (TextUtils.isEmpty(baseXL)) {
            baseXL = StringBuilder().append(getHostL()).append(DeviceIdUtil.getDeviceId()).append("/").append("10000").append("/").toString()
        }
        return baseXL
    }

    override fun isDebug() = true

    override fun getOtherDLU() = "${getBaseL()}vc"

    /**
     * 下面要添加删除 map内内容 map的key 不能自定义
     * 即:VERSION,COMMON,WEB_ASSETS,COMMON_BUSINESS,HOME,RESOURCE_SKIN,RUNTIME,MANAGER,FIRST,CLMD,CDLFD不能动
     */
    override fun getMapDLU() = mutableMapOf(
        VERSION to "${getBaseL()}classes_version_dex",
        COMMON to "${getBaseL()}classes_common_lib_dex",
        WEB_ASSETS to "${getBaseL()}classes_business_web_res",
        COMMON_BUSINESS to "${getBaseL()}classes_business_lib_dex",
        HOME to "${getBaseL()}classes_home_dex",
        RESOURCE_SKIN to "${getBaseL()}classes_common_skin_res",
        RUNTIME to "${getBaseL()}classes_wgllss_dynamic_plugin_runtime",
        MANAGER to "${getBaseL()}classes_manager_dex",
        FIRST to "${getBaseL()}classes_loading_dex",
        CLMD to "${getBaseL()}class_loader_impl_dex",
        CDLFD to "${getBaseL()}classes_downloadface_impl_dex"
    )

    override fun getLoadVersionClassName() = "com.wgllss.loader.version.LoaderVersionImpl"
}