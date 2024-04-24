package com.wgllss.core.units

import android.os.Build

object SdkIntUtils {
    fun isOreo(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    fun isMarshmallow() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    fun isLollipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    fun isJellyBeanMR2() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2

    fun isJellyBean() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN

    fun isJellyBeanMR1() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
}