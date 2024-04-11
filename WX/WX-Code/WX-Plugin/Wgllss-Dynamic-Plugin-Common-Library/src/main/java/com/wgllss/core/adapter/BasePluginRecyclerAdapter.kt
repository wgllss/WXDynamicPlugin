package com.wgllss.core.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BasePluginRecyclerAdapter<T>(protected val re: Resources, private val packageName: String) : RecyclerView.Adapter<BasePluginRecyclerAdapter.BaseBindingViewHolder>() {

    var context: Context? = null
    protected lateinit var mData: MutableList<T>
    lateinit var skinRes: Resources

    fun notifyData(mData: MutableList<T>, skinRes: Resources) {
        if (mData == null) {
            this.mData = mutableListOf()
        } else {
            this.mData = mData
        }
        this.skinRes = skinRes
        notifyDataSetChanged()
    }

    fun notifySkinRes(skinRes: Resources) {
        this.skinRes = skinRes
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

    protected abstract fun getLayoutResIdName(viewType: Int): String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        if (context == null) {
            context = parent.context
        }
        val layoutID = re.getIdentifier(getLayoutResIdName(viewType), "layout", packageName)
        val xmlResourceParser = re.getLayout(layoutID)
        val view = LayoutInflater.from(context).inflate(xmlResourceParser, parent, false)
        return BaseBindingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        val item = getItem(position)
        onBindItem(context!!, item, holder, position)
    }

    fun <T : View> findViewByID(contentView: View, resourcesPlugin: Resources, IDResName: String): T {
        val ID = resourcesPlugin.getIdentifier(IDResName, "id", packageName)
        return contentView.findViewById(ID)
    }

    fun getPluginColorID(IDResName: String): Int {
        val ID = re.getIdentifier(IDResName, "color", packageName)
        return ID
    }

    fun getPluginColor(IDResName: String): Int {
        return re.getColor(getPluginColorID(IDResName))
    }

    fun getPluginColor(re: Resources, IDResName: String, packageName: String): Int {
        return re.getColor(getPluginColorID(re, IDResName, packageName))
    }

    fun getPluginColorID(re: Resources, IDResName: String, packageName: String): Int {
        val ID = re.getIdentifier(IDResName, "color", packageName)
        return ID
    }

    protected abstract fun onBindItem(context: Context, item: T, holder: RecyclerView.ViewHolder, position: Int)

    class BaseBindingViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView)
}