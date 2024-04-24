package com.wgllss.core.units

import android.util.Log
import com.wgllss.core.re.BuildConfig


object LogTimer {
    var time: Long = System.currentTimeMillis()

    fun initTime(any: Any) {
        time = System.currentTimeMillis()
    }

    fun LogE(any: Any, tagName: String) {
        val cur = System.currentTimeMillis()
        val dis = cur - time
        if (BuildConfig.DEBUG)
            Log.e("${any.javaClass.simpleName}", " $tagName 耗时:${dis} ms")
    }
}