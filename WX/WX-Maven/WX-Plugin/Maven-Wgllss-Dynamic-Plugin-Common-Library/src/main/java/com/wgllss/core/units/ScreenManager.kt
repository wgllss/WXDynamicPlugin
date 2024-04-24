package com.wgllss.core.units

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

object ScreenManager {
    var screenWidth = 0
    var screenHeight = 0
    var widthSpec = 0
    var heightSpec = 0

    //初始化 屏幕 宽高相关属性
    fun initScreenSize(context: Context) {
        val metric = DisplayMetrics()
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getRealMetrics(metric)
        screenWidth = metric.widthPixels
        screenHeight = metric.heightPixels
        widthSpec = View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY)
        heightSpec = View.MeasureSpec.makeMeasureSpec(screenHeight, View.MeasureSpec.EXACTLY)
    }

    fun measureAndLayout(view: View) {
        view?.measure(widthSpec, heightSpec)
        view?.layout(0, 0, screenWidth, screenHeight)
    }
}