package com.wgllss.core.fragment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.wgllss.core.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

open class BaseViewModelClassFragment<VM : BaseViewModel>(@LayoutRes private val contentLayoutId: Int) : BaseFragment(contentLayoutId) {
    protected lateinit var viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel = lazyViewModels(it)
            initObserve()
        }
    }

    /**
     * activity 和 fragmnet 是否公用同一个viewModel
     */
    protected open fun activitySameViewModel() = false

    @MainThread
    private fun lazyViewModels(activity: ComponentActivity): VM {
        val cls = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        return if (activitySameViewModel()) {
            ViewModelProvider(activity.viewModelStore, activity.defaultViewModelProviderFactory).get(cls)
        } else {
            ViewModelProvider(viewModelStore, defaultViewModelProviderFactory).get(cls)
        }
    }

    /**
     * 监听当前ViewModel中 showDialog和error的值
     */
    protected open fun initObserve() {
        if (!activitySameViewModel())
            viewModel?.run {
                showUIDialog.observe(viewLifecycleOwner, Observer { it ->
                    if (it.isShow) showloading(it.msg) else hideLoading()
                })
                errorMsgLiveData.observe(viewLifecycleOwner, Observer {
                    onToast(it)
                })
            }
    }
}