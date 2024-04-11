package com.wgllss.sample.features_ui.page.other2.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.wgllss.core.widget.CommonToast

class TestBroadCast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent?.action?.run {
            when (this) {
                "action_key" -> {
                    android.util.Log.e("TestBroadCast", "收到广播")
                    CommonToast.show("收到广播")
                }
            }
        }
    }
}