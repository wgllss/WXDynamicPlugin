package com.wgllss.sample.data

import com.wgllss.sample.feature_system.untils.UUIDHelp

data class NewsBean(
    val source: String,
    val docid: String,
    val title: String,
    val commentCount: Int,
    val imgsrc3gtype: Int,
    val imgsrc: String,
    val url: String,
    val ptime: String,
    val imgextra: MutableList<ImgExtraData>
) {
    inline val id: Long
        get() = UUIDHelp.getUUID(title, source, imgsrc3gtype.toString(), ptime)
}

