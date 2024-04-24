package com.wgllss.core.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelLazy
import com.wgllss.core.units.ResourceUtils
import com.wgllss.core.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

abstract class BaseViewModelPluginFragment<VM : BaseViewModel>(private val layoutName: String, packageName: String) : BasePluginResFragment(packageName) {
    protected lateinit var containerView: View
    protected lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!this::containerView.isInitialized) {
            resourcesPlugin.run {
                val layoutID = getIdentifier(layoutName, "layout", packageName)
                val xmlResourceParser = getLayout(layoutID)
                containerView = LayoutInflater.from(inflater.context).inflate(xmlResourceParser, container, false)
                findView(inflater.context, containerView)
            }
        }
        return containerView
    }

    open fun findView(context: Context, containerView: View) {

    }

    fun <T : View> findViewByID(IDResName: String): T = containerView.findViewById(ResourceUtils.getPluginID(resourcesPlugin, IDResName, packageName))

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel = lazyViewModels(it).value
            initObserve()
        }
    }

    /**
     * activity 和 fragmnet 是否公用同一个viewModel
     */
    protected open fun activitySameViewModel() = false

    @MainThread
    private fun lazyViewModels(activity: ComponentActivity): Lazy<VM> {
        val cls = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        return if (activitySameViewModel()) ViewModelLazy(cls.kotlin, { activity.viewModelStore }, { activity.defaultViewModelProviderFactory })
        else ViewModelLazy(cls.kotlin, { viewModelStore }, { defaultViewModelProviderFactory })
    }

    /**
     * 监听当前ViewModel中 showDialog和error的值
     */
    protected open fun initObserve() {
        if (!activitySameViewModel())
            viewModel?.run {
                showUIDialog.observe(viewLifecycleOwner) {
                    if (it.isShow) showloading(it.msg) else hideLoading()
                }
                errorMsgLiveData.observe(viewLifecycleOwner) {
                    onToast(it)
                }
            }
    }
}