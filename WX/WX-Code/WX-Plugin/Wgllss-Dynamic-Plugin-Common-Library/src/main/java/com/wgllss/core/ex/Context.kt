package com.wgllss.core.ex

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.graphics.Rect
import android.os.Process
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.WindowManager
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import java.lang.reflect.Field


fun Context.toTheme(@StyleRes style: Int) = ContextThemeWrapper(this, style)

fun Context.dpToPx(dp: Float) = dp * resources.displayMetrics.density

fun Context.pxToDp(px: Float) = px / resources.displayMetrics.density

fun Context.dp2px(dipValue: Float) = (dipValue * resources.displayMetrics.density + 0.5f).toInt()

fun Context.dpToPxInt(dp: Float) = (dpToPx(dp) + 0.5f).toInt().toFloat()

fun Context.pxToDpCeilInt(px: Float) = (pxToDp(px) + 0.5f).toInt().toFloat()

fun Context.getIntToDip(intSize: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, intSize, resources.displayMetrics)

fun Activity.getStatusHight() = window.decorView.getWindowVisibleDisplayFrame(Rect())

/**
 * 得到状态栏高度
 *
 * @return
 */
fun Activity.getStatusBarHeight(): Int {
    /*
     * 方法一，荣耀3c无效 Rect frame = new Rect(); act.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame); int statusBarHeight = frame.top; return statusBarHeight;
     */

    /*
     * 方法二，荣耀3c无效 Rect rectgle= new Rect(); Window window= act.getWindow(); window.getDecorView().getWindowVisibleDisplayFrame(rectgle); int StatusBarHeight= rectgle.top; int contentViewTop=
     * window.findViewById(Window.ID_ANDROID_CONTENT).getTop(); int statusBar = contentViewTop - StatusBarHeight; return statusBar;
     */
    // 方法三，荣耀3c有效
    var c: Class<*>? = null
    var obj: Any? = null
    var field: Field? = null
    var x = 0
    var sbar = 0
    try {
        c = Class.forName("com.android.internal.R\$dimen")
        obj = c.newInstance()
        field = c.getField("status_bar_height")
        x = field[obj].toString().toInt()
        sbar = resources.getDimensionPixelSize(x)
        return sbar
    } catch (e1: Exception) {
        e1.printStackTrace()
    }
    return 0
}

fun Activity.getDensity(): Float {
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    val density: Float = dm.density//屏幕的密度density
    return density
}

fun Activity.getDensityDpi(): Int {
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    val density: Int = dm.densityDpi//屏幕的密度density
    return density
}


/**
 * 获得屏幕宽度
 *
 * @param context
 * @return
 */
fun Context.getScreenWidth(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.widthPixels
}

/**
 * 获得屏幕高度
 *
 * @param context
 * @return
 */
fun Context.getScreenHeight(): Int {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.heightPixels
}


//获取底部导航栏高度
fun Activity.getNavigationBarHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

fun Context.isUIProcess(): Boolean {
    try {
        val am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processInfos: List<ActivityManager.RunningAppProcessInfo> = am.runningAppProcesses
        val mainProcessName: String = packageName
        val myPid = Process.myPid()
        for (info in processInfos) {
            if (info.pid === myPid && mainProcessName == info.processName) {
                return true
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}