package com.wgllss.sample.features_ui.page.base

import com.wgllss.core.fragment.BaseViewModelPluginFragment
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.dynamic.sample.other2.BuildConfig
import com.wgllss.dynamic.plugin.manager.PluginResource

open class BasePluginFragment<VM : BaseViewModel>(layoutName: String) : BaseViewModelPluginFragment<VM>(layoutName,  BuildConfig.LIBRARY_PACKAGE_NAME) {

    override fun getSkinResources() = PluginResource.getSkinResources()

    override fun getPluginResources() = PluginResource.getPluginResources("classes_other2_res")!!
}