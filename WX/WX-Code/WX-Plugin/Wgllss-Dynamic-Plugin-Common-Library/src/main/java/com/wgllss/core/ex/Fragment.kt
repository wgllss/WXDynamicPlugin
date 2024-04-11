package com.wgllss.core.ex

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun Fragment.switchFragment(fragment: Fragment, mCurrentFragmentTAG: StringBuilder?, @IdRes idRes: Int) {
    if (mCurrentFragmentTAG == null || fragment.javaClass.simpleName !== mCurrentFragmentTAG.toString()) {
        childFragmentManager.apply {
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

fun Fragment.setFramgment(fragment: Fragment, layoutID: Int) {
    childFragmentManager.beginTransaction()?.let {
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

fun Fragment.HideSoftInputFromWindow(v: View) {
    (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)?.hideSoftInputFromWindow(v.windowToken, 0); //强制隐藏键盘
}