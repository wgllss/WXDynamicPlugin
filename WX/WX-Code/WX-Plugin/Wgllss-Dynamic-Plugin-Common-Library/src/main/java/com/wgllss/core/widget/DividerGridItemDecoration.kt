package com.wgllss.core.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class DividerGridItemDecoration : RecyclerView.ItemDecoration {

    var ItemDecorationView //间隔视图
            : View? = null
    var ItemDecorationViewDrawble //间隔视图的drawble
            : Drawable? = null
    var ItemDecorationViewWidth = 0 //间隔视图的宽度

    var ItemDecorationViewHeight = 0 //间隔视图的高度


    constructor(context: Context?, orientation: Int, ItemDecorationView: View?) {
        //判断传入的view以及传入的view的params是否为空，若其中一个为空则使用默认样式
        var ItemDecorationView = ItemDecorationView
        if (ItemDecorationView == null || ItemDecorationView.layoutParams == null) {
            ItemDecorationView = ImageView(context)
            ItemDecorationView.setLayoutParams(ViewGroup.LayoutParams(5, 5))
            ItemDecorationView.setBackgroundColor(Color.parseColor("#EEEEEE"))
        }
        this.ItemDecorationView = ItemDecorationView
        this.ItemDecorationView!!.measure(0, 0)
        this.ItemDecorationViewDrawble = ItemDecorationView.background
        if (this.ItemDecorationView!!.getMeasuredHeight() >= 0) {
            ItemDecorationViewHeight = this.ItemDecorationView!!.getMeasuredHeight()
        }
        if (this.ItemDecorationView!!.getMeasuredWidth() >= 0) {
            ItemDecorationViewWidth = this.ItemDecorationView!!.getMeasuredWidth()
        }
        if (ItemDecorationView.layoutParams.height >= 0) {
            ItemDecorationViewHeight = ItemDecorationView.layoutParams.height
        }
        if (ItemDecorationView.layoutParams.width >= 0) {
            ItemDecorationViewWidth = ItemDecorationView.layoutParams.width
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView) {
        drawHorizontal(c, parent!!)
        drawVertical(c, parent)
    }

    open fun getSpanCount(parent: RecyclerView): Int {
        // 列数
        var spanCount = 1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager
                .spanCount
        }
        return spanCount
    }

    open fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left = child.left - params.leftMargin
            val right = (child.right + params.rightMargin
                    + ItemDecorationViewWidth)
            val top = child.bottom + params.bottomMargin
            val bottom = top + ItemDecorationViewHeight
            ItemDecorationViewDrawble!!.setBounds(left, top, right, bottom)
            ItemDecorationViewDrawble!!.draw(c)
        }
    }

    open fun drawVertical(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        val spanCount = getSpanCount(parent)
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            val left = child.right + params.rightMargin
            var right = left + ItemDecorationViewWidth
            if ((i + 1) % spanCount == 0) { //最后一列
                right = left
            }
            ItemDecorationViewDrawble!!.setBounds(left, top, right, bottom)
            ItemDecorationViewDrawble!!.draw(c)
        }
    }

    open fun isLastColum(
        parent: RecyclerView, pos: Int, spanCount: Int,
        childCount: Int
    ): Boolean {
        var childCount = childCount
        parent.addOnItemTouchListener(object : OnRecyclerViewItemClickListener(parent) {
            override fun onItemClickListener(viewHolder: RecyclerView.ViewHolder?, position: Int){
                super.onItemClickListener(viewHolder, position)
            }

            override fun onItemLongClickListener(viewHolder: RecyclerView.ViewHolder?, position: Int) {
                super.onItemLongClickListener(viewHolder, position)
            }
        })
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) // 如果是最后一列，则不需要绘制右边
            {
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                .orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) // 如果是最后一列，则不需要绘制右边
                {
                    return true
                }
            } else {
                childCount = childCount - childCount % spanCount
                if (pos >= childCount) // 如果是最后一列，则不需要绘制右边
                    return true
            }
        } else if (layoutManager is LinearLayoutManager) {
            val orientation = layoutManager
                .orientation
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                // 如果是最后一行，则不需要绘制底部
                if (pos + 1 >= childCount) return true
            }
        }
        return false
    }

    open fun isLastRaw(
        parent: RecyclerView, pos: Int, spanCount: Int,
        childCount: Int
    ): Boolean {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            childCount = childCount - childCount % spanCount
            if (pos >= childCount) // 如果是最后一行，则不需要绘制底部
                return true
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                .orientation
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount) return true
            } else  // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true
                }
            }
        } else if (layoutManager is LinearLayoutManager) {
            val orientation = layoutManager
                .orientation
            if (orientation == LinearLayoutManager.VERTICAL) {
                // 如果是最后一行，则不需要绘制底部
                if (pos + 1 >= childCount) return true
            } else {
                return true
            }
        }
        return false
    }

    override fun getItemOffsets(
        outRect: Rect, itemPosition: Int,
        parent: RecyclerView
    ) {
        val spanCount = getSpanCount(parent)
        val childCount = parent.adapter!!.itemCount
        if (isLastRaw(parent, itemPosition, spanCount, childCount)) // 如果是最后一行，则不需要绘制底部
        {
            outRect[0, 0, ItemDecorationViewWidth] = 0
            //        } else if (isLastColum(parent, itemPosition, spanCount, childCount))// 如果是最后一列，则不需要绘制右边
//        {
//            outRect.set(0, 0, 0, ItemDecorationViewHeight);
        } else {
            outRect[0, 0, ItemDecorationViewWidth] = ItemDecorationViewHeight
        }
    }
}