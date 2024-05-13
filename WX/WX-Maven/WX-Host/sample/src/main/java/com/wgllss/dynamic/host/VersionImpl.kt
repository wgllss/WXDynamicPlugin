package com.wgllss.dynamic.host

import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant
import com.wgllss.dynamic.host.lib.version.ILoaderVersion

class VersionImpl : ILoaderVersion {

    override fun getV() = 1000 //总版本号 下面每个地方改一下 此处版本号要往上加+1

    override fun isMustShowLoading() = false

    override fun getClfd() = Triple(
        "com.wgllss.dynamic.impl.ILoadHomeImpl",
        "loading",
        1000
    )

    override fun getClmd() = Triple("", "", 0)

    override fun getCdlfd() = Triple("", "", 0)

    override fun getMapDLU() = linkedMapOf(
        DynamicPluginConstant.COMMON to Pair("classes_common_lib_dex", 1000),
        DynamicPluginConstant.WEB_ASSETS to Pair("classes_business_web_res", 1000),
        DynamicPluginConstant.COMMON_BUSINESS to Pair("classes_business_lib_dex", 1000),
        DynamicPluginConstant.RUNTIME to Pair("classes_wgllss_dynamic_plugin_runtime", 1000),
        DynamicPluginConstant.MANAGER to Pair("classes_manager_dex", 1000),
        DynamicPluginConstant.RESOURCE_SKIN to Pair("classes_common_skin_res", 1000),
        DynamicPluginConstant.HOME to Pair("classes_home_dex", 1000)
    )

    override fun getOthers() = mutableMapOf(
        "classes_other_dex" to 1000,
        "classes_other_res" to 1000,
        "classes_other2_dex" to 1000,
        "classes_other2_res" to 1000
    )
}