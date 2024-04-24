package com.wgllss.core.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.wgllss.core.activity.BaseActivity
import com.wgllss.core.widget.CommonToast

open class BaseFragment(@LayoutRes private val contentLayoutId: Int) : Fragment(contentLayoutId) {

    fun onToast(toastContent: String?) {
        CommonToast.show(toastContent)
    }

    fun showloading() {
        showloading("请稍候...")
    }

    //是否loading
    protected fun isShowloading(): Boolean? {
        return if (activity != null && activity is BaseActivity)
            (activity as BaseActivity)?.isShowloading()
        else false
    }

    fun showloading(showText: String?) {
        if (activity != null && activity is BaseActivity) {
            (activity as BaseActivity?)?.showloading(showText)
        }
    }

    fun hideLoading() {
        if (activity != null && activity is BaseActivity) {
            (activity as BaseActivity?)?.hideLoading()
        }
    }
}