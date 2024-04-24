package com.wgllss.core.ex

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun SwipeRefreshLayout.initColors() {
    setColorSchemeResources(
        android.R.color.holo_purple, android.R.color.holo_red_light,
        android.R.color.holo_orange_light, android.R.color.holo_green_light
    )
}