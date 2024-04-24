package com.wgllss.dynamic.sample.feature_home.fragment

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wgllss.core.adapter.ViewPage2ChildFragmentAdapter
import com.wgllss.core.fragment.BaseViewModelFragment
import com.wgllss.core.units.ResourceUtils
import com.wgllss.dynamic.plugin.manager.PluginResource
import com.wgllss.dynamic.sample.feature_home.pkg.ResourceContains
import com.wgllss.dynamic.sample.feature_home.viewmodels.HomeViewModel
import com.wgllss.dynamic.sample.feature_startup.startup.HomeContains
import com.wgllss.dynamic.sample.feature_startup.startup.LaunchInflateKey
import kotlinx.coroutines.runBlocking

class HomeTabFragment : BaseViewModelFragment<HomeViewModel>(ResourceContains.packageName) {

    private lateinit var childAdapter: ViewPage2ChildFragmentAdapter
    private lateinit var homeTabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var view_title_bar_bg: View
    private lateinit var rootView: View
    private var mTabLayoutMediator: TabLayoutMediator? = null

    override fun activitySameViewModel() = true

    override fun getPluginResources(): Resources? = null

    override fun getSkinResources() = PluginResource.getSkinResources()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        runBlocking {
            if (!::rootView.isInitialized) {
                rootView = HomeContains.getViewByKey(inflater.context, LaunchInflateKey.home_tab_fragment_layout)!!
                homeTabLayout = rootView.findViewById(getSkinPluginID("homeTabLayout"))
                viewPager2 = rootView.findViewById(getSkinPluginID("homeViewPager2"))
                view_title_bar_bg = rootView.findViewById(getSkinPluginID("view_title_bg"))
            }
            rootView?.parent?.takeIf {
                it is ViewGroup
            }?.let {
                (it as ViewGroup).removeView(rootView)
            }
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.lazyTabViewPager2.observe(viewLifecycleOwner) {
            childAdapter = ViewPage2ChildFragmentAdapter(childFragmentManager, lifecycle)
            viewPager2.adapter = childAdapter
            mTabLayoutMediator = TabLayoutMediator(homeTabLayout, viewPager2) { tab: TabLayout.Tab, position: Int ->
                val textView = TextView(requireContext())
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
                ResourceUtils.setTextColor(getSkinResources(), "colorOnPrimary", ResourceContains.packageName, textView)
                tab.customView = textView
                textView.text = (childAdapter.list[position] as HomeFragment).title
            }.apply(TabLayoutMediator::attach)
            homeTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    tab?.customView?.takeIf {
                        it is TextView
                    }?.run {
                        (this as TextView).run {
                            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
//                            setTextColor(resources.getColor(R.color.colorPrimary))
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    tab?.customView?.takeIf {
                        it is TextView
                    }?.run {
                        (this as TextView).run {
                            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
//                            setTextColor(resources.getColor(R.color.white))
                        }
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }
            })
            childAdapter.notifyData(getList())
            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position > 0 && viewPager2.offscreenPageLimit != childAdapter.itemCount)
                        viewPager2.offscreenPageLimit = childAdapter.itemCount
                }
            })
        }
    }

    private fun getList() = mutableListOf<Fragment>(
        HomeFragment.newInstance("手机", "BAI6I0O5wangning"),
        HomeFragment.newInstance("新闻", "BBM54PGAwangning"),
        HomeFragment.newInstance("娱乐", "BA10TA81wangning"),
        HomeFragment.newInstance("体育", "BA8E6OEOwangning"),
        HomeFragment.newInstance("时尚", "BA8F6ICNwangning"),
        HomeFragment.newInstance("财经", "BA8EE5GMwangning"),
        HomeFragment.newInstance("汽车", "BA8DOPCSwangning"),
        HomeFragment.newInstance("军事", "BAI67OGGwangning"),
        HomeFragment.newInstance("科技", "BA8D4A3Rwangning"),
    )

    override fun onDetach() {
        super.onDetach()
        mTabLayoutMediator?.detach()
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", ResourceContains.packageName, view_title_bar_bg)
        homeTabLayout?.run {
            setSelectedTabIndicatorColor(ResourceUtils.getPluginColor(skinRes, "colorOnPrimary", ResourceContains.packageName))
            for (i in 0 until tabCount) {
                getTabAt(i)?.customView?.takeIf {
                    it is TextView
                }?.let {
                    ResourceUtils.setTextColor(skinRes, "colorOnPrimary", ResourceContains.packageName, it as TextView)
                }
            }
        }
    }
}