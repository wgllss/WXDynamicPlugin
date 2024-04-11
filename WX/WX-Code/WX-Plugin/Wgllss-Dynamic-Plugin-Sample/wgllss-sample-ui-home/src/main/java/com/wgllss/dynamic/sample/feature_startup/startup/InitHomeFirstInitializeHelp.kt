package com.wgllss.dynamic.sample.feature_startup.startup

import android.content.Context
import android.content.MutableContextWrapper
import com.tencent.mmkv.MMKV
import com.wgllss.core.ex.toTheme
import com.wgllss.core.units.ScreenManager
import com.wgllss.dynamic.plugin.manager.PluginResource
import com.wgllss.dynamic.sample.feature_home.fragment.HomeTabFragment
import com.wgllss.sample.feature_system.savestatus.MMKVHelp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object InitHomeFirstInitializeHelp {

    fun initCreate(activity: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            ScreenManager.initScreenSize(activity)
            MMKV.initialize(activity)

            val themeID = activity.resources.getIdentifier("Theme.WXDynamicPlugin", "style", activity.packageName)
            val context: Context = MutableContextWrapper(activity.toTheme(themeID))
            val res = PluginResource.getSkinResources()
            HomeContains.putViewByKey(LaunchInflateKey.home_activity, async {
                GenerateHomeLayout.syncCreateHomeActivityLayout(context, res)
            })
            HomeContains.putViewByKey(LaunchInflateKey.home_navigation, async {
                GenerateHomeLayout.syncCreateHomeNavigationLayout(context, res)
            })
            HomeContains.putFragmentByKey(LaunchInflateKey.home_tab_fragment, async {
                HomeTabFragment()
            })
            HomeContains.putViewByKey(LaunchInflateKey.home_tab_fragment_layout, async {
                GenerateHomeLayout.syncCreateHomeTabFragmentLayout(context, res)
            })
            HomeContains.putViewByKey(LaunchInflateKey.home_fragment, async {
                GenerateHomeLayout.syncCreateHomeFragmentLayout(context, res)
            })
            async(Dispatchers.IO) {
                val am = PluginResource.getWebRes().assets
                try {
                    val resJs = am.list("js")
                    val strOfflineResources = StringBuilder()
                    if (resJs != null && resJs.isNotEmpty()) {
                        for (i in resJs.indices) {
                            strOfflineResources.append(resJs[i])
                        }
                    }
                    val resCss = am.list("css")
                    if (resCss != null && resCss.isNotEmpty()) {
                        for (i in resCss.indices) {
                            strOfflineResources.append(resCss[i])
                        }
                    }
                    MMKVHelp.saveJsPath(strOfflineResources.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}