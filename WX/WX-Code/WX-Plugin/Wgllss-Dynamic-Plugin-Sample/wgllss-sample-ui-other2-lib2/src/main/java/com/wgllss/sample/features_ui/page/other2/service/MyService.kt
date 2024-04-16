package com.wgllss.sample.features_ui.page.other2.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.wgllss.dynamic.runtime.library.WXHostServiceDelegate

class MyService : WXHostServiceDelegate {

    private lateinit var context: Context

    override fun attachBaseContext(newBase: Context) {
        this.context = newBase
        android.util.Log.e("MyService", "START_NOT_STICKY attachBaseContext")
    }

    override fun onBind(intent: Intent?): IBinder? {
        android.util.Log.e("MyService", "START_NOT_STICKY onBind")
        return null
    }

    override fun onCreate() {
        android.util.Log.e("MyService", "START_NOT_STICKY onCreate")
    }

    override fun onStart(intent: Intent?, startId: Int) {
        val str = intent?.getStringExtra("service_key") ?: ""
        android.util.Log.e("MyService", "START_NOT_STICKY onStart: str :$str")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val str = intent?.getStringExtra("service_key") ?: ""
        android.util.Log.e("MyService", "START_NOT_STICKY onStartCommand: str :$str")


        return Service.START_NOT_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        val str = intent?.getStringExtra("service_key") ?: ""
        android.util.Log.e("MyService", "START_NOT_STICKY onUnbind: str :$str")
        return false
    }

    override fun onDestroy() {
        android.util.Log.e("MyService", "START_NOT_STICKY onDestroy: ")
    }

    override fun callMethodByID(methodID: Int): String {
        return when (methodID) {
            1 -> {
                ""
            }
            else -> {
                ""
            }
        }
    }
}