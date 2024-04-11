package com.wgllss.dynamic.impl

import android.content.Context
import com.wgllss.dynamic.host.lib.home.ILoadHome
import com.wgllss.dynamic.sample.feature_startup.startup.InitHomeFirstInitializeHelp

class ILoadHomeImpl : ILoadHome {

    override fun loadHomeCreate(context: Context) {
        InitHomeFirstInitializeHelp.initCreate(context)
    }
}