package com.wgllss.dynamic.ui

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wgllss.core.activity.BaseViewModelActivity
import com.wgllss.core.ex.switchFragment
import com.wgllss.core.units.ResourceUtils
import com.wgllss.dynamic.plugin.manager.PluginManager
import com.wgllss.dynamic.plugin.manager.PluginResource
import com.wgllss.dynamic.sample.feature_startup.startup.HomeContains
import com.wgllss.dynamic.sample.feature_startup.startup.LaunchInflateKey
import com.wgllss.dynamic.sample.feature_home.pkg.ResourceContains
import com.wgllss.dynamic.sample.feature_home.viewmodels.HomeViewModel
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.home.fragment.CollectFragment
import com.wgllss.sample.features_ui.page.home.fragment.SampleFragment
import com.wgllss.sample.features_ui.page.home.fragment.SettingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

open class HomeActivity : BaseViewModelActivity<HomeViewModel>(ResourceContains.packageName) {

    private val collectFragmentL by lazy { CollectFragment() }

    private val sampleFragmentL by lazy { SampleFragment() }

    private val settingFragmentL by lazy { SettingFragment() }

    private lateinit var homeTabFragment: Fragment

    private lateinit var navigationView: BottomNavigationView

    private lateinit var contentLayout: View

    override fun getPluginResources(): Resources? = null

    override fun getSkinResources() = PluginResource.getSkinResources()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(Bundle())
    }

    override fun initControl(savedInstanceState: Bundle?) {
        runBlocking {
            contentLayout = HomeContains.getViewByKey(this@HomeActivity, LaunchInflateKey.home_activity)!!
            addContentView(contentLayout, contentLayout.layoutParams)
            if (savedInstanceState == null) {
                initFragment()
                setCurrentFragment(homeTabFragment)
            } else {
                initNavigation()
                onNavBarItemSelected(navigationView.menu.getItem(getItemId()).itemId)
            }
        }
    }


    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        exitApp()
    }

    override fun lazyInitValue() {
        window.setBackgroundDrawable(null)//去掉主题背景颜色
        if (viewModel.isFirst) {
            viewModel.lazyTabView()
            viewModel.isFirst = false
        }
        initNavigation()
        addContentView(navigationView, navigationView.layoutParams)
        initNavigation(navigationView)
        lifecycleScope.launch(Dispatchers.IO) {
            PluginManager.instance.deleteOldFile()
        }
    }

    private fun initFragment() {
        runBlocking {
            if (!::homeTabFragment.isInitialized) {
                homeTabFragment = HomeContains.getFragmentByKey(LaunchInflateKey.home_tab_fragment)
            }
        }
    }

    private fun initNavigation() {
        runBlocking {
            if (!::navigationView.isInitialized) {
                navigationView = HomeContains.getViewByKey(this@HomeActivity, LaunchInflateKey.home_navigation)!! as BottomNavigationView
            }
        }
    }

    private fun initNavigation(bottomNavigationView: BottomNavigationView) {
        bottomNavigationView.apply {
            with(menu) {
                get(0).icon = getPluginDrawable(getSkinResources(), "ic_home_black_24dp", SkinContains.packageName)
                get(1).icon = getPluginDrawable(getSkinResources(), "ic_baseline_grade_24", SkinContains.packageName)
                get(2).icon = getPluginDrawable(getSkinResources(), "ic_dashboard_black_24dp", SkinContains.packageName)
                get(3).setIcon(getPluginDrawable(getSkinResources(), "ic_notifications_black_24dp", SkinContains.packageName))
            }
            if (viewModel.isFirst) viewModel.isFirst = false else selectedItemId = menu.getItem(getItemId()).itemId
            setOnItemSelectedListener {
                return@setOnItemSelectedListener onNavBarItemSelected(it.itemId)
            }
        }
    }


    private fun getItemId() = when (viewModel.mCurrentFragmentTAG.toString()) {
        "HomeFragment" -> 0
        "CollectFragment" -> 1
        "SampleFragment" -> 2
        "SettingFragment" -> 3
        else -> 0
    }

    private fun onNavBarItemSelected(itemId: Int): Boolean {
        when (itemId) {
            getPluginID(getSkinResources(), "fmt_a", SkinContains.packageName) -> {
                initFragment()
                setCurrentFragment(homeTabFragment)
            }

            getPluginID(getSkinResources(), "fmt_b", SkinContains.packageName) -> {
                setCurrentFragment(collectFragmentL)
            }

            getPluginID(getSkinResources(), "fmt_c", SkinContains.packageName) -> {
                setCurrentFragment(sampleFragmentL)
            }

            getPluginID(getSkinResources(), "fmt_d", SkinContains.packageName) -> {
                setCurrentFragment(settingFragmentL)
            }
        }
        return true
    }

    private fun setCurrentFragment(fragment: Fragment) {
        switchFragment(fragment, viewModel.mCurrentFragmentTAG, getPluginID(getSkinResources(), "nav_host_fragment_activity_main", SkinContains.packageName))
        viewModel.mCurrentFragmentTAG.delete(0, viewModel.mCurrentFragmentTAG.toString().length)
        viewModel.mCurrentFragmentTAG.append(fragment.javaClass.simpleName)
    }

    override fun onChangeSkin(skinRes: Resources) {
        if (!viewModel.isFirst) {
            ResourceUtils.setBackgroundColor(skinRes, "colorBackground", ResourceContains.packageName, contentLayout)
            val colorList = ResourceUtils.getColorStatusList(skinRes, "bottom_navigation_color_selector", ResourceContains.packageName)
            navigationView.itemTextColor = colorList
            navigationView.itemIconTintList = colorList
        }
    }
}