package com.wgllss.core.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.wgllss.core.dialog.CommonLoadingView
import com.wgllss.core.ex.finishActivity
import com.wgllss.core.units.StatusBarUtil
import com.wgllss.core.widget.CommonToast

abstract class BaseActivity : FragmentActivity() {
    private var loading: CommonLoadingView? = null
    private var initFlag = false
    open fun hasNavigationBarStatusBarTranslucent() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        if (hasNavigationBarStatusBarTranslucent()) StatusBarUtil.setStatusBarTranslucent(this)
        beforeSuperOnCreate(savedInstanceState);
        super.onCreate(savedInstanceState)
        initX(savedInstanceState)
    }

    protected open fun initX(savedInstanceState: Bundle?) {
        initControl(savedInstanceState)
        bindEvent()
        initValue()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            if (!initFlag) {
                initFlag = true
                lazyInitValue()
            }
        }
    }

    fun onToast(content: String) {
        CommonToast.show(content);
    }

    //是否loading
    open fun isShowloading(): Boolean? {
        return loading?.isShowing()
    }

    open fun showloading(showText: String?) {
        if (null == loading) loading = CommonLoadingView(this)
        if (isShowloading() == true) return
        if (showText != null) loading?.show(showText)
    }

    open fun hideLoading() {
        loading?.dismiss()
        loading = null
    }

    override fun onDestroy() {
        hideLoading()
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishActivity()
    }

    open fun beforeSuperOnCreate(savedInstanceState: Bundle?) {}
    open abstract fun initControl(savedInstanceState: Bundle?)
    open abstract fun bindEvent()
    open abstract fun initValue()
    open fun lazyInitValue() {}


    private var exitTime: Long = 0

    protected open fun exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            onToast("再按一次退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            WActivityManager.exitApplication()
        }
    }
}