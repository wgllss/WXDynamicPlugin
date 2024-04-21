package com.wgllss.dynamic.sample.feature_home.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wgllss.core.adapter.BasePluginRecyclerAdapter
import com.wgllss.core.adapter.BaseRecyclerAdapter
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.ex.loadUrl
import com.wgllss.core.units.ScreenManager
import com.wgllss.sample.data.NewsBean
import com.wgllss.sample.feature_system.globle.Constants
import com.wgllss.sample.features_ui.page.base.SkinContains

class HomeNewsAdapter(re: Resources, packageName: String) : BasePluginRecyclerAdapter<NewsBean>(re, packageName) {

    private val id1 = 1
    private val id2 = 2
    private val id3 = 3

    private val id31 = 31
    private val id32 = 32

    private val id21 = 21
    private val id22 = 22
    private val id231 = 231
    private val id232 = 232
    private val id233 = 233

    private var cornerRadiusInt: Int = 0
    private val textColor by lazy { Color.parseColor("#999999") }
    private val footer by lazy { NewsBean("", "", 0, 300, "", "", "", mutableListOf()) }
    fun addFooter() {
        mData.add(footer)
        notifyItemInserted(mData.size - 1)
    }

    fun removeFooter() {
        mData.removeAt(mData.size - 1)
        notifyItemRemoved(mData.size)
    }

    override fun getLayoutResIdName(viewType: Int) = ""

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else {
            if (getItem(position).imgsrc3gtype == 2) 2
            else if (getItem(position).imgsrc3gtype == 300) 3
            else 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        if (context == null) {
            context = parent.context
            cornerRadiusInt = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 999f, parent.context.resources.displayMetrics).toInt()
        }
        val layout = when (viewType) {
            0 -> {
                val frameLayout = FrameLayout(context!!).apply {
                    layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, context.getIntToDip(136f).toInt())
                    val array: IntArray = intArrayOf(android.R.attr.selectableItemBackground)
                    val typedValue = TypedValue()
                    val attr = context.theme.obtainStyledAttributes(typedValue.resourceId, array)
                    foreground = attr.getDrawable(0)!!
                    attr.recycle()
                    isClickable = true
                    isFocusable = true
                }

                val img = ImageView(parent.context).apply {
                    id = id31
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                    scaleType = ImageView.ScaleType.FIT_XY
                }
                frameLayout.addView(img)

                val title = TextView(context).apply {
                    id = id32
                    val size = context.getIntToDip(20f).toInt()
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, size).apply {
                        gravity = Gravity.BOTTOM or Gravity.LEFT
                    }
                    gravity = Gravity.CENTER_VERTICAL
                    setTextColor(Color.WHITE)
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)
                    setBackgroundColor(Color.parseColor("#90000000"))
                }
                frameLayout.addView(title)
                frameLayout
            }
            2 -> {
                val frameLayout = FrameLayout(context!!).apply {
                    layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, context.getIntToDip(136f).toInt())
                    val array: IntArray = intArrayOf(android.R.attr.selectableItemBackground)
                    val typedValue = TypedValue()
                    val attr = context.theme.obtainStyledAttributes(typedValue.resourceId, array)
                    foreground = attr.getDrawable(0)!!
                    attr.recycle()
                    isClickable = true
                    isFocusable = true
                    val size = context.getIntToDip(20f).toInt()
                    setPadding(size, 0, size, 0)
                }

                val title = TextView(context).apply {
                    id = id21
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, context.getIntToDip(26f).toInt()).apply {
                        gravity = Gravity.TOP or Gravity.LEFT
                    }
                    gravity = Gravity.CENTER_VERTICAL
                    setTextColor(getPluginColor(skinRes, "textColorPrimary", SkinContains.packageName))
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
                }
                frameLayout.addView(title)

                val height = parent.context.getIntToDip(86f).toInt()
                val margin = parent.context.getIntToDip(20f).toInt()
                val width = (ScreenManager.screenWidth - 2 * margin) / 3 - parent.context.getIntToDip(3f).toInt()

                val img1 = ImageView(context).apply {
                    id = id231
                    layoutParams = FrameLayout.LayoutParams(width, height).apply {
                        gravity = Gravity.TOP or Gravity.LEFT
                        topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26f, context.resources.displayMetrics).toInt()
                    }
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
                frameLayout.addView(img1)

                val img2 = ImageView(context).apply {
                    id = id232
                    layoutParams = FrameLayout.LayoutParams(width, height).apply {
                        gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                        topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26f, context.resources.displayMetrics).toInt()
                    }
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
                frameLayout.addView(img2)

                val img3 = ImageView(context).apply {
                    id = id233
                    layoutParams = FrameLayout.LayoutParams(width, height).apply {
                        gravity = Gravity.TOP or Gravity.RIGHT
                        topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26f, context.resources.displayMetrics).toInt()
                    }
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
                frameLayout.addView(img3)

                val time = TextView(context).apply {
                    id = id22
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, context.getIntToDip(20f).toInt()).apply {
                        gravity = Gravity.BOTTOM or Gravity.LEFT
                    }
                    gravity = Gravity.CENTER_VERTICAL
                    setTextColor(textColor)
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)
                    maxLines = 1
                }
                frameLayout.addView(time)
                frameLayout
            }
            3 -> {
                ProgressBar(context).apply {
                    layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT).apply {
                        topMargin = context.getIntToDip(5f).toInt()
                    }
                }
            }
            else -> {
                val frameLayout = FrameLayout(context!!).apply {
                    layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, context.getIntToDip(86f).toInt())
                    val array: IntArray = intArrayOf(android.R.attr.selectableItemBackground)
                    val typedValue = TypedValue()
                    val attr = context.theme.obtainStyledAttributes(typedValue.resourceId, array)
                    foreground = attr.getDrawable(0)!!
                    attr.recycle()
                    isClickable = true
                    isFocusable = true
                    val size = context.getIntToDip(20f).toInt()
                    setPadding(size, 0, size, 0)
                }

                val img = ImageView(context!!).apply {
                    id = id1
                    val size = context.getIntToDip(80f).toInt()
                    layoutParams = FrameLayout.LayoutParams(size, size).apply {
                        gravity = Gravity.TOP or Gravity.LEFT
                        topMargin = context.getIntToDip(3f).toInt()
                    }
                    scaleType = ImageView.ScaleType.FIT_XY
                }
                frameLayout.addView(img)

                val textViewName = TextView(context).apply {
                    id = id2
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, context.getIntToDip(45f).toInt()).apply {
                        gravity = Gravity.TOP or Gravity.LEFT
                        leftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90f, context.resources.displayMetrics).toInt()
                        topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, context.resources.displayMetrics).toInt()
                    }
                    gravity = Gravity.CENTER_VERTICAL
                    setTextColor(getPluginColor(skinRes, "textColorPrimary", SkinContains.packageName))
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
                    maxLines = 2
                }
                frameLayout.addView(textViewName)

                val time = TextView(context).apply {
                    id = id3
                    layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, context.getIntToDip(26f).toInt()).apply {
                        gravity = Gravity.BOTTOM or Gravity.LEFT
                        leftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90f, context.resources.displayMetrics).toInt()
                    }
                    gravity = Gravity.CENTER_VERTICAL
                    setTextColor(textColor)
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)
                    maxLines = 1
                }
                frameLayout.addView(time)
                frameLayout
            }
        }

        return BaseBindingViewHolder(layout)
    }

    override fun onBindItem(context: Context, item: NewsBean, holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            0 -> {
                holder.itemView.findViewById<ImageView>(id31).loadUrl(item.imgsrc)
                holder.itemView.findViewById<TextView>(id32).text = item.title
            }
            2 -> {
                holder.itemView.findViewById<TextView>(id21).text = item.title
                holder.itemView.findViewById<TextView>(id22).text = "${item.source}  ${item.ptime}"
                holder.itemView.findViewById<ImageView>(id231).loadUrl(item.imgsrc)
                holder.itemView.findViewById<ImageView>(id232).loadUrl(item.imgextra[0].imgsrc)
                holder.itemView.findViewById<ImageView>(id233).loadUrl(item.imgextra[1].imgsrc)
            }
            3 -> {

            }
            else -> {
                if (TextUtils.isEmpty(item.imgsrc)) {
                    holder.itemView.findViewById<ImageView>(id1).setImageResource(Constants.defaultImgID)
                    //todo Glide 里面可以配置 加载失败图片，这里只是 实例一下 这么写，可以这样直接用到宿主 资源
                } else {
                    holder.itemView.findViewById<ImageView>(id1).loadUrl(item.imgsrc)
                }
                holder.itemView.findViewById<TextView>(id2).text = item.title
                holder.itemView.findViewById<TextView>(id3).text = "${item.source}  ${item.ptime}"
            }
        }
    }
}