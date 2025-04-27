package com.wgllss.loader.version

import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.COMMON
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.COMMON_BUSINESS
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.HOME
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.MANAGER
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.RESOURCE_SKIN
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.RUNTIME
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.WEB_ASSETS
import com.wgllss.dynamic.host.lib.version.ILoaderVersion

class LoaderVersionImpl : ILoaderVersion {

    override fun getV() = 1000

    override fun isMustShowLoading() = false

    override fun getClfd() = Triple(
        "com.wgllss.dynamic.impl.ILoadHomeImpl", "loading", 1000
    )

    override fun getClmd() = Triple("", "", 0)

    override fun getCdlfd() = Triple("", "", 0)

//    override fun getClmd() = Triple("com.wgllss.dynamic.plugin.loader.LoaderManagerImpl", "class_loader_impl_dex", 1000)
//
//    override fun getCdlfd() = Triple("com.wgllss.dynamic.plugin.download_face.DownLoadFaceImpl", "classes_downloadface_impl_dex", 1000)

    override fun getMapDLU() = linkedMapOf(
        COMMON to Pair("classes_common_lib_dex", 1000),
        WEB_ASSETS to Pair("classes_business_web_res", 1000),
        COMMON_BUSINESS to Pair("classes_business_lib_dex", 1001),
        RUNTIME to Pair("classes_wgllss_dynamic_plugin_runtime", 1000),
        MANAGER to Pair("classes_manager_dex", 1000),
        RESOURCE_SKIN to Pair("classes_common_skin_res", 1000),
        HOME to Pair("classes_home_dex", 1000)
    )

//    override fun getOthers() = mutableMapOf<String, Int>()

    override fun getOthers() = mutableMapOf(
        "classes_other_dex" to 1000, "classes_other_res" to 1000, "classes_other2_dex" to 1000, "classes_other2_res" to 1000
    )

}