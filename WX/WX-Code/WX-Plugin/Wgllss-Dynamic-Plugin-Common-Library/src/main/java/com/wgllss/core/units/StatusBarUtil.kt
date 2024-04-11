package com.wgllss.core.units

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.view.ViewCompat
import com.wgllss.core.ex.getIntToDip
import java.lang.reflect.Method
import java.util.*

/**
 * 屏幕相关工具类
 *   1.设置沉浸式任务栏
 *
 * @author LTP 16/9/21.
 */
object StatusBarUtil {

    /**
     * 模拟沉浸式状态栏，本质上是通过设置状态栏的颜色，可设置为与toolbar相同达到沉浸式的效果
     *
     * @param activity 要设置的Activity
     */
//    fun setImmersionStatus(activity: Activity) {
//        // 透明状态栏
//        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        activity.window.statusBarColor = activity.resources.getColor(R.color.purple_500)
//    }

    /**
     * 设置无状态栏，直接干掉顶部的状态栏，但要注意例如一些actionbar会自动顶到最上方需要适配
     *
     * @param activity 要设置的Activity
     */
    fun setNoStatus(activity: Activity) {
        // 透明状态栏
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    fun setStatusBarTranslucent(activity: Activity) {
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

    /**
     * 设置状态栏  导航栏 透明
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     */
    fun setNavigationBarStatusBarTranslucent(activity: Activity) {
        activity?.window?.apply {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            navigationBarColor = Color.TRANSPARENT
            statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * 设置Android6.0上状态栏的字体颜色为黑色
     *
     * @param activity Activity
     */
    fun setStatusBarLightMode(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    /**
     * 设置Android6.0上状态栏的字体颜色为黑色
     *
     * @param activity Activity
     */
    fun setStatusBarDarkMode(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    /**
     * 获取手机状态栏的高度
     */
    fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    fun translucentStatusBar(activity: Activity, toolbar: ViewGroup? = null) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.statusBarColor = Color.TRANSPARENT
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                val mContentView = window.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
                val mChildView = mContentView.getChildAt(0)
                if (mChildView != null) {
                    ViewCompat.setFitsSystemWindows(mChildView, false)
                    ViewCompat.requestApplyInsets(mChildView)
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 透明状态栏
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && toolbar != null) {
                val statusBarHeight: Int = getStatusBarHeight(activity)
                val layoutHeight = (if (statusBarHeight > 0) statusBarHeight else 36) + activity.getIntToDip(42f) as Int
                val lp = toolbar.layoutParams
                lp.height = layoutHeight
                toolbar.setPadding(0, statusBarHeight, 0, 0)
            }
        } catch (e: Exception) {
        }
    }

    fun translucentActivity(activity: Activity) {
        try {
            activity.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            activity.window.decorView.background = null
            val activityOptions: Method = Activity::class.java.getDeclaredMethod("getActivityOptions")
            activityOptions.isAccessible = true
            val option = activityOptions.invoke(activity)
            val classes = Activity::class.java.declaredClasses
            var aClass: Class<out Any>? = null
            classes.forEach {
                if (it.simpleName.contains("TranslucentConversionListener")) {
                    aClass = it
                }
            }
            val method: Method = Activity::class.java.getDeclaredMethod("convertToTranslucent", aClass, ActivityOptions::class.java)
            method.isAccessible = true
            method.invoke(activity, null, option)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
