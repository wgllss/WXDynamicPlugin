package com.wgllss.sample.features_ui.page.home.adapter

import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textview.MaterialTextView
import com.wgllss.core.adapter.BasePluginRecyclerAdapter
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.ex.loadUrl
import com.wgllss.sample.feature_system.room.table.CollectTableBean
import com.wgllss.sample.features_ui.page.base.SkinContains

class CollectionAdapter(re: Resources, packageName: String) : BasePluginRecyclerAdapter<CollectTableBean>(re, packageName) {
    override fun getLayoutResIdName(viewType: Int) = "adapter_collection_item"

    override fun onBindItem(context: Context, item: CollectTableBean, holder: RecyclerView.ViewHolder, position: Int) {
        item.run {
            findViewByID<ShapeableImageView>(holder.itemView, re, "collection_icon").apply {
                loadUrl(imgUrl)
                shapeAppearanceModel = ShapeAppearanceModel.builder().apply {
                    setAllCorners(RoundedCornerTreatment())
                    setAllCornerSizes(context.getIntToDip(5f)) //设置圆， 40为正方形边长 80 一半，等于半径 ，需要注意单位
                }.build()
            }
            findViewByID<MaterialTextView>(holder.itemView, re, "txt_title").apply {
                text = title
                setTextColor(getPluginColor(skinRes, "textColorPrimary", SkinContains.packageName))
            }
        }
    }
}