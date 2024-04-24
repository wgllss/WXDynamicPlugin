package com.wgllss.sample.feature_system.untils

import java.util.*
import kotlin.math.abs

object UUIDHelp {

    fun getUUID(title: String, author: String, requestRealUrl: String, pic: String): Long {
        val sb = StringBuilder()
        sb.append(title).append(author).append(requestRealUrl).append(pic)
        val uuLongID = sb.toString().hashCode().toLong()
        return abs(UUID(uuLongID, uuLongID).toString().replace("-", "").hashCode()).toLong()
    }
}