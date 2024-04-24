package com.wgllss.dynamic.system.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.wgllss.dynamic.ui.HomeActivity
import com.wgllss.dynamic.system.broadcast.BroadCastAction.DOWNLOAD_HOME_COMPLETE_ACTION

class DownLoadCompleteBroadCast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent?.action?.run {
            when (this) {
                DOWNLOAD_HOME_COMPLETE_ACTION -> {
                    try {
                        val clazz = Class.forName(HomeActivity::class.java.name)
                        context.startActivity(Intent(context, clazz))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}