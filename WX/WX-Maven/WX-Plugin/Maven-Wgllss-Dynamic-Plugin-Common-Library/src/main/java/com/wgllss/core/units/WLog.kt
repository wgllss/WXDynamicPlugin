package com.wgllss.core.units

import com.wgllss.core.BuildConfig

object WLog {

    fun e(any: Any, message: String) {
        if (BuildConfig.DEBUG)
            android.util.Log.e(any.javaClass.simpleName, message)
    }
}