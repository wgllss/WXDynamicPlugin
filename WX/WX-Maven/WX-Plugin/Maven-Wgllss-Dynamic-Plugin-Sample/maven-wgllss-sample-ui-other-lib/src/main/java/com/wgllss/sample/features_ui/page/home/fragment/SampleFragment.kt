package com.wgllss.sample.features_ui.page.home.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.ex.parseErrorString
import com.wgllss.core.units.ResourceUtils
import com.wgllss.core.widget.DividerGridItemDecoration
import com.wgllss.core.widget.OnRecyclerViewItemClickListener
import com.wgllss.dynamic.plugin.manager.PluginManager
import com.wgllss.sample.data.SampleItemBean
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.home.adapter.SampleAdapter
import com.wgllss.sample.features_ui.page.home.viewmodels.SampleViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SampleFragment : BasePluginFragment<SampleViewModel>("fragment_sample") {

    private lateinit var rv_s_list: RecyclerView;
    private lateinit var view_title_bar_bg: View
    private lateinit var title: TextView
    private lateinit var sampleAdapter: SampleAdapter

    override fun findView(context: Context, containerView: View) {
        rv_s_list = findViewByID("rv_s_list")
        view_title_bar_bg = findViewByID("view_title_bar_bg")
        title = findViewByID("title")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_s_list.run {
            layoutManager = LinearLayoutManager(context)
            sampleAdapter = SampleAdapter(resourcesPlugin, packageName)
            adapter = sampleAdapter
            addOnItemTouchListener(object : OnRecyclerViewItemClickListener(this) {

                override fun onItemClickListener(itemRootView: View, position: Int) {
                    onItemClick(sampleAdapter.getItem(position))
                }
            })
            setHasFixedSize(true)
            val itemDecoration = View(context)
            val size = context.getIntToDip(1.0f).toInt()
            itemDecoration.layoutParams = ViewGroup.LayoutParams(size, size)
            itemDecoration.setBackgroundColor(Color.parseColor("#60000000"))
            addItemDecoration(DividerGridItemDecoration(context, GridLayoutManager.VERTICAL, itemDecoration))
        }
    }

    override fun initObserve() {
        super.initObserve()
        viewModel.run {
            start()
            sampleList.observe(viewLifecycleOwner) {
                sampleAdapter.notifyData(it, getSkinResources())
            }
        }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, view_title_bar_bg)
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, title)
    }

    override fun callChangeSkin(skinRes: Resources) {
        super.callChangeSkin(skinRes)
        sampleAdapter?.notifySkinRes(skinRes)
    }

    private fun onItemClick(item: SampleItemBean) {
        activity?.run {
            /** 方案一写法 other2 首次下载写法 start  **/
            showloading("请稍后...")
            val status = PluginManager.instance.isLoadSuccessByKey("classes_other2_dex", "classes_other2_res")
            hideLoading()
            if (!status) {
                onToast("缺少插件")
                return
            }
            itemID(this, item)
            /** 方案一写法 other2 首次下载写法 end  **/

            /** 方案二写法 other2 点击时候下载安装 start  **/
            lifecycleScope.launch {
                PluginManager.instance.dynamicLoadPlugin(this@run, Pair("classes_other2_dex", 1000), Pair("classes_other2_res", 1000))
                    .onStart { showloading("加载中...") }.onCompletion { hideLoading() }.catch {
                        onToast(it.parseErrorString())
                    }.collect {
                        itemID(this@run, item)
                    }
            }
            /** 方案二写法 other2 首次下载写法 end  **/
        }
    }

    private fun itemID(activity: Activity, item: SampleItemBean) {
        when (item.id) {
            5 -> { //webview
                PluginManager.instance.startPluginSingleTaskActivity(activity, "classes_other2_res", "com.wgllss.sample.features_ui.page.other2.activity.WebViewActivity", "com.wgllss.dynamic.sample.other2", Intent().apply {
                    putExtra("web_url_key", "I4D2IC730011819H")
                    putExtra("title_key", "红魔五周年发布")
                    putExtra("docid_key", "I4D2IC730011819H.html")
                })
            }

            9 -> {
                //音频
                PluginManager.instance.startPluginSingleTaskActivity(
                    activity, "classes_other2_res", "com.wgllss.sample.features_ui.page.other2.activity.AudioActivity", "com.wgllss.dynamic.sample.other2"
                )
            }

            8 -> {
                //视频
                PluginManager.instance.startPluginSingleTaskActivity(
                    activity, "classes_other2_res", "com.wgllss.sample.features_ui.page.other2.activity.VideoActivity", "com.wgllss.dynamic.sample.other2"
                )
            }

            10 -> {
                //compose
                PluginManager.instance.startPluginStandardComposeActivity(activity, "classes_other2_res", "com.wgllss.sample.features_ui.page.other2.activity.ComposeDemoActivity", "com.wgllss.dynamic.sample.other2", Intent().apply {
                    putExtra("action_type", item.id)
                    putExtra("itemName", item.itemName)
                })
            }

            else -> {
                PluginManager.instance.startStandardActivity(activity, "classes_other2_res", "com.wgllss.sample.features_ui.page.other2.activity.Other2Activity", "com.wgllss.dynamic.sample.other2", Intent().apply {
                    putExtra("action_type", item.id)
                    putExtra("itemName", item.itemName)
                })
            }
        }
    }
}
