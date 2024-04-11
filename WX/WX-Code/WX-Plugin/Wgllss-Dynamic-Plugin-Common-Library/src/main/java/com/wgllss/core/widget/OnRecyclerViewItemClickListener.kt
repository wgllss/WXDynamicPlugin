package com.wgllss.core.widget

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

open class OnRecyclerViewItemClickListener constructor(val recyclerView: RecyclerView) : RecyclerView.OnItemTouchListener {

    private lateinit var gestureDetectorCompat: GestureDetectorCompat

    init {
        gestureDetectorCompat = GestureDetectorCompat(recyclerView.context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                trasfomerItemEvent(e, 1)
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                trasfomerItemEvent(e, 0)
                return true
            }
        })
    }

    private fun trasfomerItemEvent(e: MotionEvent, type: Int) {
        recyclerView?.let {
            it.findChildViewUnder(e.x, e.y)
        }?.apply {
            val viewHolder = recyclerView.getChildViewHolder(this)
            val position = recyclerView.getChildPosition(this)
            if (type == 0) {
                onItemClickListener(this, position)
            } else {
                onItemLongClickListener(this, position)
            }
        }
    }

    open fun onItemClickListener(itemRootView: View, position: Int) {}

    open fun onItemLongClickListener(itemRootView: View, position: Int) {}

    open fun onItemClickListener(viewHolder: RecyclerView.ViewHolder?, position: Int) {}

    open fun onItemLongClickListener(viewHolder: RecyclerView.ViewHolder?, position: Int) {}

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        gestureDetectorCompat.onTouchEvent(e)
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        gestureDetectorCompat.onTouchEvent(e)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

}