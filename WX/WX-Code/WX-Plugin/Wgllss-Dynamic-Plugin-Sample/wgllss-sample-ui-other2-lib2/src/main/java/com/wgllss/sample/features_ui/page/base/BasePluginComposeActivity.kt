package com.wgllss.sample.features_ui.page.base

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.wgllss.core.activity.compose.BaseComposeActivity
import com.wgllss.dynamic.plugin.manager.PluginResource
import com.wgllss.dynamic.runtime.library.WXHostActivityDelegate

open class BasePluginComposeActivity : BaseComposeActivity(), WXHostActivityDelegate {

    private var isPlugin = false
    protected lateinit var activity: ComponentActivity
    private lateinit var resourcesP: Resources
    private lateinit var mDefaultFactory: ViewModelProvider.Factory

    fun getSkinResources() = PluginResource.getSkinResources()

//    fun getPluginResources() = PluginResource.getPluginResources("classes_other2_res")!!

    override fun attachContext(context: FragmentActivity, resources: Resources) {
        isPlugin = true
        activity = context
        resourcesP = resources
        attachBaseContext(context)
    }

    override fun attachContext(context: ComponentActivity, resources: Resources) {
        isPlugin = true
        activity = context
        resourcesP = resources
        attachBaseContext(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        if (!isPlugin) {
            super.onCreate(savedInstanceState)
        }
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
    }

    override fun onStart() {
        if (!isPlugin) {
            super.onStart()
        }
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

    override val viewModelStore: ViewModelStore
        get() = activity.viewModelStore

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() {
            if (!this::mDefaultFactory.isInitialized) {
                mDefaultFactory = SavedStateViewModelFactory(
                    activity.application, activity, if (activity.intent != null) activity.intent.extras else null
                )
            }
            return mDefaultFactory
        }

    override fun lazyInitValue() {
        if (!isPlugin) {

        }

    }
}