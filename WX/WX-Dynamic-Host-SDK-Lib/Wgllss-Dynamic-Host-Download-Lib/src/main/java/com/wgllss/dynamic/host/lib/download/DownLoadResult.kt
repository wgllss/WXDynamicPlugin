package com.wgllss.dynamic.host.lib.download

import android.content.Context

data class DownLoadResult(
    val context: Context,
    val contentKey: String,
    val fileAbsolutePath: String
)
