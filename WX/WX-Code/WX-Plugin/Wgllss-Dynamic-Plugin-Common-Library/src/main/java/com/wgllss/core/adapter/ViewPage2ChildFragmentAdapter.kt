package com.wgllss.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPage2ChildFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    lateinit var list: MutableList<Fragment>

    fun notifyData(mData: MutableList<Fragment>) {
        if (mData == null) {
            list = mutableListOf()
        } else {
            this.list = mData
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = if (!this::list.isInitialized) 0 else list.size

    override fun createFragment(position: Int) = list[position]
}