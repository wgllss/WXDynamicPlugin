package com.wgllss.core.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseRecyclerAdapter.BaseBindingViewHolder>() {
    var context: Context? = null
    private lateinit var mData: MutableList<T>

    fun notifyData(mData: MutableList<T>) {
        if (mData == null) {
            this.mData = mutableListOf()
        } else {
            this.mData = mData
        }
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        mData?.takeIf {
            it.size > position
        }?.run {
            removeAt(position)
            notifyDataSetChanged()
        }
    }

    fun clearList() {
        mData?.run {
            clear()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = if (!this::mData.isInitialized) 0 else mData.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getItem(position: Int): T = mData[position]

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        val item = getItem(position)
        onBindItem(context!!, item, holder, position)
    }

    protected abstract fun onBindItem(context: Context, item: T, holder: RecyclerView.ViewHolder, position: Int)

    class BaseBindingViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}