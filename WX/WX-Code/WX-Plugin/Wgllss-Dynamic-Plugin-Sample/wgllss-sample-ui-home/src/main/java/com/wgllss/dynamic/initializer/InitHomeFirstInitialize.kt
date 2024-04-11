package com.wgllss.dynamic.initializer

import android.content.Context
import androidx.startup.Initializer
import com.wgllss.dynamic.sample.feature_startup.startup.InitHomeFirstInitializeHelp

class InitHomeFirstInitialize : Initializer<Unit> {

    override fun create(activity: Context) {
        InitHomeFirstInitializeHelp.initCreate(activity)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}