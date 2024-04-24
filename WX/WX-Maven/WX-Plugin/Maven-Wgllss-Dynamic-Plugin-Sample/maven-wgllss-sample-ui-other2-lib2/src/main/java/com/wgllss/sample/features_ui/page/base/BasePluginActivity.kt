package com.wgllss.sample.features_ui.page.base

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.wgllss.core.activity.BaseActivity
import com.wgllss.core.activity.BaseViewModePluginActivity
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.dynamic.sample.other2.BuildConfig
import com.wgllss.dynamic.runtime.library.WXHostActivityDelegate
import com.wgllss.dynamic.plugin.manager.PluginResource

open class BasePluginActivity<VM : BaseViewModel>(layoutName: String) : BaseViewModePluginActivity<VM>(layoutName, BuildConfig.LIBRARY_PACKAGE_NAME), WXHostActivityDelegate {

    private var isPlugin = false
    protected lateinit var activity: FragmentActivity
    private lateinit var resourcesP: Resources
    private lateinit var mDefaultFactory: ViewModelProvider.Factory

    override fun getSkinResources() = PluginResource.getSkinResources()

    override fun getPluginResources() = PluginResource.getPluginResources("classes_other2_res")!!


    override fun attachContext(context: FragmentActivity, resources: Resources) {
        isPlugin = true
        activity = context
        resourcesP = resources
        attachBaseContext(context)
    }

    override fun bindEvent() {
        viewModel?.run {
            showUIDialog.observe(activity) {
                activity.takeIf { it is BaseActivity }?.run {
                    if (it.isShow) {
                        (activity as BaseActivity).showloading(it.msg)
                    } else {
                        (activity as BaseActivity).hideLoading()
                    }
                }
            }
            errorMsgLiveData.observe(activity) {
                onToast(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!isPlugin) {
            super.onCreate(savedInstanceState)
        }
        initX(savedInstanceState)
    }

    override fun onResume() {
        if (!isPlugin) {
            super.onResume()
        }
    }

    override fun onRestart() {
        if (!isPlugin) {
            super.onRestart()
        }
        onChangeSkin(getSkinResources())
    }

    override fun onStart() {
        if (!isPlugin) {
            super.onStart()
        }
        onChangeSkin(getSkinResources())
    }

    override fun onPause() {
        if (!isPlugin) {
            super.onPause()
        }
    }

    override fun onStop() {
        if (!isPlugin) {
            super.onStop()
        }
    }

    override fun onDestroy() {
        if (!isPlugin) {
            super.onDestroy()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (!isPlugin) {
            super.onSaveInstanceState(outState)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (!isPlugin) {
            super.onConfigurationChanged(newConfig)
        }
    }

    override fun getResources() = activity.resources

    override fun <T : View> findViewById(id: Int): T {
        return activity.findViewById(id)
    }

    override fun getIntent() = activity.intent

    override fun getWindow() = activity.window

    override fun setRequestedOrientation(requestedOrientation: Int) {
        activity.requestedOrientation = requestedOrientation
    }

    override fun getViewModelStore() = activity.viewModelStore

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        if (!this::mDefaultFactory.isInitialized) {
            mDefaultFactory = SavedStateViewModelFactory(
                activity.application, activity,
                if (activity.intent != null) activity.intent.extras else null
            )
        }
        return mDefaultFactory
    }

    override fun lazyInitValue() {
        if (!isPlugin) {
            super.lazyInitValue()
        }
    }
}