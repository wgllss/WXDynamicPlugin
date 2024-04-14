package com.wgllss.dynamic.sample.feature_startup.startup

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.tabs.TabLayout
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.ex.initColors
import com.wgllss.core.widget.DividerGridItemDecoration
import com.wgllss.core.widget.clearLongClickToast
import com.wgllss.core.units.ResourceUtils
import com.wgllss.core.units.ScreenManager
import com.wgllss.dynamic.plugin.manager.PluginResource
import com.wgllss.dynamic.sample.feature_home.pkg.ResourceContains

object GenerateHomeLayout {

    fun getCreateViewByKey(context: Context, key: String) = when (key) {
        LaunchInflateKey.home_activity -> syncCreateHomeActivityLayout(context, PluginResource.getSkinResources())
        LaunchInflateKey.home_navigation -> syncCreateHomeNavigationLayout(context, PluginResource.getSkinResources())
        LaunchInflateKey.home_tab_fragment_layout -> syncCreateHomeTabFragmentLayout(context, PluginResource.getSkinResources())
        LaunchInflateKey.home_fragment -> syncCreateHomeFragmentLayout(context, PluginResource.getSkinResources())
        else -> null
    }

    private fun getPluginID(res: Resources, IDResName: String) = ResourceUtils.getPluginID(res, IDResName, ResourceContains.packageName)

    private fun getStringValue(res: Resources, IDResName: String) = ResourceUtils.getPluginString(res, IDResName, ResourceContains.packageName)

    private fun getDimenValue(res: Resources, IDResName: String) = ResourceUtils.getPluginDimen(res, IDResName, ResourceContains.packageName).toInt()

    private fun getColorStatusList(res: Resources, resName: String) = ResourceUtils.getColorStatusList(res, resName, ResourceContains.packageName)

    fun syncCreateHomeActivityLayout(context: Context, res: Resources): View {
        val activityLayout = FragmentContainerView(context).apply {
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            setPadding(0, 0, 0, getDimenValue(res, "navigation_height"))
            layoutParams = lp
//            setBackgroundColor(ThemeUtils.getAndroidColorBackground(context))
            setBackgroundColor(ResourceUtils.getPluginColor(res, "colorBackground", ResourceContains.packageName))
            id = getPluginID(res, "nav_host_fragment_activity_main")// R.id.nav_host_fragment_activity_main
        }
        ScreenManager.measureAndLayout(activityLayout)
        return activityLayout
    }

    fun syncCreateHomeNavigationLayout(context: Context, res: Resources): View {
        val bottomNavigationView = BottomNavigationView(context).apply {
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getDimenValue(res, "navigation_height"))
            lp.gravity = Gravity.BOTTOM or Gravity.LEFT
            layoutParams = lp
            id = getPluginID(res, "buttom_navigation")// R.id.buttom_navigation
            setBackgroundColor(Color.TRANSPARENT)
            labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
            menu.apply {
                clear()
                add(0, getPluginID(res, "fmt_a"), 0, getStringValue(res, "title_home"))
                add(0, getPluginID(res, "fmt_b"), 0, getStringValue(res, "title_collection"))
                add(0, getPluginID(res, "fmt_c"), 0, getStringValue(res, "title_sample"))
                add(0, getPluginID(res, "fmt_d"), 0, getStringValue(res, "title_setting"))
            }
            val colorList = getColorStatusList(res, "bottom_navigation_color_selector")
            itemTextColor = colorList
            itemIconTintList = colorList
            clearLongClickToast(
                getPluginID(res, "fmt_a"),
                getPluginID(res, "fmt_b"),
                getPluginID(res, "fmt_c"),
                getPluginID(res, "fmt_d")
            )
        }
        ScreenManager.measureAndLayout(bottomNavigationView)
        return bottomNavigationView
    }

    fun syncCreateHomeTabFragmentLayout(context: Context, res: Resources): View {
        val tabFragmentLayout = FrameLayout(context).apply {
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            layoutParams = lp
        }
        val viewTitleBg = View(context).apply {
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getDimenValue(res, "title_bar_height"))
            lp.gravity = Gravity.TOP or Gravity.LEFT
            layoutParams = lp
            id = getPluginID(res, "view_title_bg")
//            setBackgroundColor(ThemeUtils.getColorPrimary(context))
            setBackgroundColor(ResourceUtils.getPluginColor(res, "colorPrimary", ResourceContains.packageName))
        }
        tabFragmentLayout.addView(viewTitleBg)
        val tabLayout = TabLayout(context).apply {
            id = getPluginID(res, "homeTabLayout")//  R.id.homeTabLayout
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getDimenValue(res, "title_bar_text_height"))
            lp.gravity = Gravity.TOP or Gravity.LEFT
            lp.topMargin = getDimenValue(res, "status_bar_height") //res.getDimension(R.dimen.status_bar_height).toInt()
            layoutParams = lp
            setBackgroundColor(Color.TRANSPARENT)
            tabMode = TabLayout.MODE_SCROLLABLE
            tabGravity = TabLayout.GRAVITY_CENTER
            setSelectedTabIndicatorColor(ResourceUtils.getPluginColor(res, "colorOnPrimary", ResourceContains.packageName))
            setSelectedTabIndicatorHeight(8)
        }
        tabFragmentLayout.addView(tabLayout)
        val viewPager2Layout = ViewPager2(context).apply {
            id = getPluginID(res, "homeViewPager2")//R.id.homeViewPager2
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            lp.gravity = Gravity.TOP or Gravity.LEFT
            lp.topMargin = getDimenValue(res, "title_bar_height") //res.getDimension(R.dimen.title_bar_height).toInt()
            layoutParams = lp
        }
        tabFragmentLayout.addView(viewPager2Layout)
        ScreenManager.measureAndLayout(tabFragmentLayout)
        return tabFragmentLayout
    }

    fun syncCreateHomeFragmentLayout(context: Context, res: Resources): View {
        val swipeRefreshLayout = SwipeRefreshLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            initColors()
        }
        val homeFragmentView = RecyclerView(context).apply {
            id = getPluginID(res, "home_recycle_view")//R.id.home_recycle_view
            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            lp.gravity = Gravity.TOP or Gravity.LEFT
            layoutParams = lp
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            val itemDecoration = View(context)
            val size = context.getIntToDip(1.0f).toInt()
            itemDecoration.layoutParams = ViewGroup.LayoutParams(size, size)
            itemDecoration.setBackgroundColor(Color.parseColor("#60000000"))
            addItemDecoration(DividerGridItemDecoration(context, GridLayoutManager.VERTICAL, itemDecoration))
//            val json = MMKVHelp.getHomeTab1Data()
//            json?.let {
//                val homeMusicAdapter = HomeNewsAdapter(res, ResourceContains.packageName)
//                adapter = homeMusicAdapter
//                homeMusicAdapter.notifyData(Gson().fromJson(json, object : TypeToken<MutableList<NewsBean>>() {}.type), res)
//            }
        }
        swipeRefreshLayout.addView(homeFragmentView)
        return swipeRefreshLayout
    }
}