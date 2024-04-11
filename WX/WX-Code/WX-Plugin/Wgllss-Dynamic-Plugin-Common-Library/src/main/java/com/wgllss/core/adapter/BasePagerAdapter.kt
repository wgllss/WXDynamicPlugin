package com.wgllss.core.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class BasePagerAdapter(val mViews: MutableList<View>) : PagerAdapter() {
    override fun getCount() = mViews?.size ?: 0

    override fun isViewFromObject(view: View, obj: Any) = view === obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        mViews?.takeIf { it.size > 0 }?.let { container.removeView(it[position]) }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(mViews[position])
        return mViews[position]
    }
}