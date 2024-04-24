package com.wgllss.sample.features_ui.playing.activity

import android.app.Activity
import android.os.Bundle
import com.wgllss.core.ex.finishActivity

class NotificationTargetActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finishActivity()
    }
}