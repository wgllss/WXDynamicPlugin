package com.wgllss.sample.features_ui.page.home.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.wgllss.core.adapter.BasePluginRecyclerAdapter
import com.wgllss.sample.data.SampleItemBean
import com.wgllss.sample.features_ui.page.base.SkinContains
import kotlin.random.Random

class SampleAdapter(re: Resources, packageName: String) : BasePluginRecyclerAdapter<SampleItemBean>(re, packageName) {

    private var cornerRadiusInt: Int = 0
    private val textColor by lazy { Color.parseColor("#999999") }
    private lateinit var array: Array<Int>

    override fun getLayoutResIdName(viewType: Int) = "adapter_sample_item"

    override fun onBindItem(context: Context, item: SampleItemBean, holder: RecyclerView.ViewHolder, position: Int) {
        if (!this::array.isInitialized) {
            array = arrayOf(
                getPluginColor(skinRes, "color_random_0", SkinContains.packageName), getPluginColor(skinRes, "color_random_1", SkinContains.packageName), getPluginColor(skinRes, "color_random_2", SkinContains.packageName),
                getPluginColor(skinRes, "color_random_3", SkinContains.packageName), getPluginColor(skinRes, "color_random_4", SkinContains.packageName), getPluginColor(skinRes, "color_random_5", SkinContains.packageName),
                getPluginColor(skinRes, "color_random_6", SkinContains.packageName), getPluginColor(skinRes, "color_random_7", SkinContains.packageName), getPluginColor(skinRes, "color_random_8", SkinContains.packageName),
                getPluginColor(skinRes, "color_random_9", SkinContains.packageName), getPluginColor(skinRes, "color_random_10", SkinContains.packageName), getPluginColor(skinRes, "color_random_11", SkinContains.packageName)
            )
        }
        item.run {
            findViewByID<MaterialButton>(holder.itemView, re, "material_btn_name").apply {
                text = item.itemName
                setTextColor(getPluginColor(skinRes, "colorOnPrimary", SkinContains.packageName))
                background.setTint(array[Random.nextInt(array.size)])
                if (cornerRadiusInt == 0)
                    cornerRadiusInt = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 999f, context.resources.displayMetrics).toInt() else 0
                cornerRadius = cornerRadiusInt
            }
            findViewByID<MaterialTextView>(holder.itemView, re, "material_item_name").apply {
                text = item.itemName
                setTextColor(getPluginColor(skinRes, "textColorPrimary", SkinContains.packageName))
            }
        }
    }
}