package com.wgllss.sample.features_ui.page.home.fragment

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.units.ResourceUtils
import com.wgllss.core.widget.DividerGridItemDecoration
import com.wgllss.core.widget.OnRecyclerViewItemClickListener
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.home.adapter.CollectionAdapter
import com.wgllss.sample.features_ui.page.home.viewmodels.CollectViewModel

class CollectFragment : BasePluginFragment<CollectViewModel>("fragment_collection") {

    private lateinit var rv_c_list: RecyclerView;
    private lateinit var view_title_bar_bg: View
    private lateinit var title: TextView
    private lateinit var collectionAdapter: CollectionAdapter

    override fun findView(context: Context, containerView: View) {
        rv_c_list = findViewByID("rv_c_list")
        view_title_bar_bg = findViewByID("view_title_bar_bg")
        title = findViewByID("title")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_c_list.run {
            layoutManager = GridLayoutManager(context, 3)
            collectionAdapter = CollectionAdapter(resourcesPlugin, packageName)
            adapter = collectionAdapter
            addOnItemTouchListener(object : OnRecyclerViewItemClickListener(this) {
                override fun onItemLongClickListener(itemRootView: View, position: Int) {
                    viewModel.deleteFromId(collectionAdapter.getItem(position).id)
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
            isInitSuccess.observe(viewLifecycleOwner) {
                liveData.observe(viewLifecycleOwner) {
                    collectionAdapter.notifyData(it, getSkinResources())
                }
            }
        }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, view_title_bar_bg)
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, title)
    }

    override fun callChangeSkin(skinRes: Resources) {
        super.callChangeSkin(skinRes)
        collectionAdapter?.notifySkinRes(skinRes)
    }
}
