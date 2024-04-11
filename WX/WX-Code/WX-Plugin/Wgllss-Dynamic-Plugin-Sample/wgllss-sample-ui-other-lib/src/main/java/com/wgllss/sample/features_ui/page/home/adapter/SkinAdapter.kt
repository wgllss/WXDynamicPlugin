package com.wgllss.sample.features_ui.page.home.adapter

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.wgllss.core.adapter.BaseRecyclerAdapter
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.units.ResourceUtils
import com.wgllss.sample.datasource.SkinPluginBean

class SkinAdapter : BaseRecyclerAdapter<SkinPluginBean>() {
    private var cornerRadiusInt: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        if (context == null) {
            context = parent.context
            cornerRadiusInt = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 999f, parent.context.resources.displayMetrics).toInt()
        }
        val textView = MaterialButton(parent.context).apply {
            val size = context.getIntToDip(60f).toInt()
            val lp = RecyclerView.LayoutParams(size, size)
            layoutParams = lp
            insetBottom = 0
            insetTop = 0
            cornerRadius = cornerRadiusInt
        }
        return BaseBindingViewHolder(textView)
    }

    override fun onBindItem(context: Context, item: SkinPluginBean, holder: RecyclerView.ViewHolder, position: Int) {
        ResourceUtils.setMaterialButtonBackgroundHint("#600000", item.skinColor, holder.itemView as MaterialButton)
    }
}