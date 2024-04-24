package com.wgllss.core.ex

import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


fun FragmentActivity.switchFragment(fragment: Fragment, mCurrentFragmentTAG: StringBuilder?, @IdRes idRes: Int) {
    if (mCurrentFragmentTAG == null || fragment.javaClass.simpleName !== mCurrentFragmentTAG.toString()) {
        supportFragmentManager.apply {
            val f = findFragmentByTag(mCurrentFragmentTAG.toString())
            beginTransaction()?.let {
                f?.let { f -> it.hide(f) }
                fragment?.apply {
                    if (!isAdded) {
                        userVisibleHint = true;
                        it.add(idRes, this, javaClass.simpleName).show(this)
                    } else {
                        it.show(this)
                    }
                }
                it.commitAllowingStateLoss()
            }
        }
    }
}

fun FragmentActivity.setFramgment(fragment: Fragment, layoutID: Int) {
    supportFragmentManager.beginTransaction()?.let {
        fragment?.apply {
            if (!isAdded) {
                userVisibleHint = true;
                it.add(layoutID, this, javaClass.simpleName).show(this)
            } else {
                it.show(this)
            }
        }
        it.commitAllowingStateLoss()
    }
}

fun FragmentActivity.hideSoftInputFromWindow(v: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)?.hideSoftInputFromWindow(v.windowToken, 0); //强制隐藏键盘
}

fun FragmentActivity.setFullScreen() {
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }
}

fun ComponentActivity.registerForActivityResultPermissionsLaunch(permission: Array<String>, blockPerSuccess: () -> Unit, blockPerFail: () -> Unit = {}) {
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { it ->
        var successCount = 0
        it.forEach { k, v ->
            if (k != null && v) {
                successCount++
            }
        }
        if (successCount == permission.size) {
            blockPerSuccess()
        } else {
            blockPerFail()
        }
    }.launch(permission)
}