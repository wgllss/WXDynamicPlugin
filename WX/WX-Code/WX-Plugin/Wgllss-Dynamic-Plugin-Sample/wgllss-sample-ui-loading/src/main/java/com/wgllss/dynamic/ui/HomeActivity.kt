package com.wgllss.dynamic.ui

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import com.wgllss.core.ex.finishActivity
import com.wgllss.dynamic.system.broadcast.BroadCastAction

class HomeActivity : Activity() {

    private val downLoadCompleteBroadCast by lazy { DownLoadCompleteBroadCast() }

    private fun setStatusBarTranslucent(activity: Activity) {
        activity?.window?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                //适配刘海屏
                val layoutParams = attributes
                layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                attributes = layoutParams
            }
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarTranslucent(this)
        val imageView = ImageView(this).apply {
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            layoutParams = lp
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        imageView.setImageResource(com.wgllss.host.skin.R.drawable.loading)
        setContentView(imageView)
        val intentFilter = IntentFilter(BroadCastAction.DOWNLOAD_HOME_COMPLETE_ACTION)
        registerReceiver(downLoadCompleteBroadCast, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        downLoadCompleteBroadCast?.run {
            unregisterReceiver(this)
        }
    }

    inner class DownLoadCompleteBroadCast : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            intent?.action?.run {
                when (this) {
                    BroadCastAction.DOWNLOAD_HOME_COMPLETE_ACTION -> {
                        try {
                            val clazz = Class.forName(HomeActivity::class.java.name)
                            context.startActivity(Intent(context, clazz))
                            this@HomeActivity.finishActivity()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}