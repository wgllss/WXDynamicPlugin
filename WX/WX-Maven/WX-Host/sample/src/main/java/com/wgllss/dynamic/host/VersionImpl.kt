package com.wgllss.dynamic.host

import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant
import com.wgllss.dynamic.host.lib.version.ILoaderVersion

/**
 * 版本控制 宿主默认有此一份,首次需要和
 */
class VersionImpl : ILoaderVersion {

    override fun getV() = 1000 //总版本号 只要下面每个地方改一下 此处版本号要往上加+1，下面可同一时间改多个，上面加一下版本号

    override fun isMustShowLoading() = false//下次下载插件 是否显示主下载loading 页面

    override fun getClfd() = Triple(
        //配置loading 页插件实现 及版本号 对应 maven-wgllss-sample-loader-version 工程
        "com.wgllss.dynamic.impl.ILoadHomeImpl", "loading", 1000
    )

    //配置 动态实现根据版本下载插件 及版本号 对应 Maven-Wgllss-Dynamic-Plugin-Loader-Impl 工程
    override fun getClmd() = Triple("", "", 0)

    //动态实现更换下载插件地址，文件，已经debug  Maven-Wgllss-Dynamic-Plugin-DownloadFace-Impl
    override fun getCdlfd() = Triple("", "", 0)


    /**
     * 下面要添加删除 map内内容 map的key 不能自定义
     * 即:COMMON,WEB_ASSETS,COMMON_BUSINESS,RUNTIME,MANAGER,RESOURCE_SKIN,HOME不能动
     */
    override fun getMapDLU() = linkedMapOf(
        DynamicPluginConstant.COMMON to Pair("classes_common_lib_dex", 1000), //Maven-Wgllss-Dynamic-Plugin-Common-Library 插件工程 和 版本号
        DynamicPluginConstant.WEB_ASSETS to Pair("classes_business_web_res", 1000), //maven-wgllss-sample-assets-source-apk 插件工程 和版本号
        DynamicPluginConstant.COMMON_BUSINESS to Pair("classes_business_lib_dex", 1000),//maven-wgllss-sample-business-library 插件工程 和 版本号
        DynamicPluginConstant.RUNTIME to Pair("classes_wgllss_dynamic_plugin_runtime", 1000), //Maven-Wgllss-Dynamic-Plugin-RunTime-Apk 插件工程 和 版本号
        DynamicPluginConstant.MANAGER to Pair("classes_manager_dex", 1000), // Maven-Wgllss-Dynamic-Plugin-Manager 插件工程 和 版本号
        DynamicPluginConstant.RESOURCE_SKIN to Pair("classes_common_skin_res", 1000), // maven-wgllss-sample-skin-resource-apk 插件工程 和 版本号
        DynamicPluginConstant.HOME to Pair("classes_home_dex", 1000) //maven-wgllss-sample-ui-home 插件工程 和 版本号
    )

//    override fun getOthers() = mutableMapOf<String, Int>()

    override fun getOthers() = mutableMapOf(
        "classes_other_dex" to 1000,  //maven-wgllss-sample-ui-other-lib 插件工程 和 版本号
        "classes_other_res" to 1000,  //maven-wgllss-sample-ui-other 插件工程 和 版本号
        "classes_other2_dex" to 1000, //maven-wgllss-sample-ui-other2-lib2 插件工程 和 版本号
        "classes_other2_res" to 1000  //maven-wgllss-sample-ui-other2 插件工程 和 版本号
    )
}