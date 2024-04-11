package com.wgllss.dynamic.host.lib.runtime

data class InstalledApk(
    val apkFilePath: String,
    var oDexPath: String,
    var libraryPath: String? = null
)
