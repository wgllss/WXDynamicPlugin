package com.wgllss.dynamic.host

import android.app.Application
import android.content.Context
import com.wgllss.core.units.AppGlobals
import com.wgllss.core.units.LogTimer
import com.wgllss.dynamic.host.lib.impl.WXDynamicLoader

class SampleApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        LogTimer.initTime(this)
        AppGlobals.sApplication = this
        WXDynamicLoader.instance.installPlugin(base, FaceImpl(), VersionImpl())
    }
}