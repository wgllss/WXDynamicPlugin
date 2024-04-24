package com.wgllss.dynamic.plugin.download_face

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
    override fun getHostL() = "http://192.168.3.21:8080/assets/"
//    override fun getHostL() = "http://192.168.1.9:8080/assets/"

    override fun getBaseL() = "${getHostL()}10000_Test/"

    override fun isDebug() = true

    override fun getOtherDLU() = "${getBaseL()}vc"

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