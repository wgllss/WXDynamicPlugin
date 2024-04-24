package com.wgllss.core.fragment

import android.content.Context
import android.content.res.Resources
import android.view.View
import com.wgllss.core.units.ResourceUtils

abstract class BasePluginResFragment(protected val packageName: String) : BaseFragment(0) {

    protected lateinit var resourcesPlugin: Resources

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getPluginResources()?.let {
            resourcesPlugin = it
        }
    }

    override fun onStart() {
        super.onStart()
        onChangeSkin(getSkinResources())
    }

    abstract fun getPluginResources(): Resources?

    abstract fun getSkinResources(): Resources

    fun <T : View> findViewByID(contentView: View, IDResName: String): T = contentView.findViewById(ResourceUtils.getPluginID(resourcesPlugin, IDResName, packageName))

    protected fun getPluginID(resName: String) = ResourceUtils.getPluginID(resourcesPlugin, resName, packageName)

    protected fun getSkinPluginID(resName: String) = ResourceUtils.getPluginID(getSkinResources(), resName, packageName)

    protected fun getPluginID(resources: Resources, resName: String) = ResourceUtils.getPluginID(resources, resName, packageName)

    open fun onChangeSkin(skinRes: Resources) {

    }

    open fun callChangeSkin(skinRes: Resources) {
        onChangeSkin(skinRes)
        childFragmentManager.fragments.forEach {
            if (it.isAdded && it is BasePluginResFragment) {
                it.callChangeSkin(skinRes)
            }
        }
    }
}