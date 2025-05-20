package com.wgllss.dynamic.host.lib.version

interface ILoaderVersion {
    /**总版本号*/
    fun getV(): Int

    /** 必须要更新宿主，或者下载耗时，显示loading 页面 **/
    fun isMustShowLoading(): Boolean

    /**
     * class load first dex
     * 首次加载 实现 dex
     * first:启动页之后的第一个首页实现类
     * second:加载本地文件名称
     * third:版本号
     **/
    fun getClfd(): Triple<String, String, Int>

    /**
     * class loader manager dex
     * 动态管理器 (管理插件的加载 和下载逻辑) 加载 实现 dex
     * first:加载器实现的类名 (全路径)
     * second:加载本地文件名称
     * third:版本号
     **/
    fun getClmd(): Triple<String, String, Int>

    /**
     * class download face dex
     * 动态下载接口实现 dex
     * first:动态插件 下载接口实现 类的类名  (全路径)
     * second:加载本地文件名称
     * third:版本号
     **/
    fun getCdlfd(): Triple<String, String, Int>

    /** 宿主必备的dex **/
    fun getMapDLU(): MutableMap<String, Pair<String, Int>>

    /** other dex*/
    fun getOthers(): MutableMap<String, Int>

}