package com.wgllss.dynamic.host.lib.download

interface IDynamicDownLoadFace {

    fun getHostL(): String

    fun getBaseL(): String

    fun isDebug(): Boolean

    fun getOtherDLU(): String

    fun getMapDLU(): MutableMap<String, String>

    fun getLoadVersionClassName(): String

}