package com.wgllss.sample.features_ui.page.home.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.units.ResourceUtils
import com.wgllss.core.widget.DividerGridItemDecoration
import com.wgllss.core.widget.OnRecyclerViewItemClickListener
import com.wgllss.dynamic.plugin.manager.PluginManager
import com.wgllss.sample.data.SampleItemBean
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.home.adapter.SampleAdapter
import com.wgllss.sample.features_ui.page.home.viewmodels.SampleViewModel

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
            if (item.id == 5) {
                PluginManager.instance.startStandardActivity(
                    this, "classes_other2_res",
                    "com.wgllss.sample.features_ui.page.other2.activity.WebViewActivity",
                    "com.wgllss.dynamic.sample.other2", Intent().apply {
                        putExtra("action_type", item.id)
                        putExtra("itemName", item.itemName)
                        putExtra("web_url_key", "I4D2IC730011819H")
                        putExtra("title_key", "红魔五周年发布")
                        putExtra("docid_key", "I4D2IC730011819H.html")
                    }
                )
            } else {
                PluginManager.instance.startStandardActivity(
                    this, "classes_other2_res",
                    "com.wgllss.sample.features_ui.page.other2.activity.Other2Activity",
                    "com.wgllss.dynamic.sample.other2", Intent().apply {
                        putExtra("action_type", item.id)
                        putExtra("itemName", item.itemName)
                    }
                )
            }
        }
    }
}
