package com.wgllss.core.widget

import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * 处理BottomNavigationView中的tab长按出现toast的问题
 *
 * @param ids tab项的id集
 */
fun BottomNavigationView.clearLongClickToast(vararg ids: Int) {
    val bottomNavigationView: ViewGroup = getChildAt(0) as ViewGroup
    for (position in ids.indices) {
        bottomNavigationView.getChildAt(position).findViewById<View>(ids[position])
            .setOnLongClickListener { true }
    }
}