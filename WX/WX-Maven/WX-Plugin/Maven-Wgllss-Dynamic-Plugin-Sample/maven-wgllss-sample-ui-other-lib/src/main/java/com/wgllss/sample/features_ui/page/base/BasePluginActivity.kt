package com.wgllss.sample.features_ui.page.base

import android.content.res.Resources
import com.wgllss.core.activity.BaseViewModePluginActivity
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.dynamic.sample.other.BuildConfig
import com.wgllss.dynamic.plugin.manager.PluginResource

open class BasePluginActivity<VM : BaseViewModel>(layoutName: String) : BaseViewModePluginActivity<VM>(layoutName, BuildConfig.LIBRARY_PACKAGE_NAME) {

    override fun getPluginResources(): Resources? = PluginResource.getPluginResources("classes_other_res")

    override fun getSkinResources() = PluginResource.getSkinResources()
}