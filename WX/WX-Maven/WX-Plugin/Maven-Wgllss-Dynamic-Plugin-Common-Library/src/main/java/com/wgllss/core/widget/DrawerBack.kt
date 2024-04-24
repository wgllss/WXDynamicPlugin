package com.wgllss.core.widget

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Handler
import android.view.*
import android.view.animation.Interpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Scroller

class DrawerBack(activity: Activity) : FrameLayout(activity) {
    companion object {
        private const val SLIDE_TARGET_CONTENT = 0
        private const val SLIDE_TARGET_WINDOW = 1
        private const val SCROLL_DURATION = 400
        private const val TOUCH_TARGET_WIDTH_DIP = 30
        private const val TOUCH_HANDLE_WIDTH_DIP = 1.33f // 屏幕左侧4分之3
    }

    private var mAdded = false
    private var mDrawerEnabled = true
    private var mDrawerOpened = false
    private var mDrawerMoving = false
    private var mGestureStarted = false
    private var mDecorOffsetX = 0
    private var mGestureStartX = 0
    private var mGestureCurrentX = 0
    private var mGestureStartY = 0
    private var mGestureCurrentY = 0
    private var mSlideTarget = 0
    private var mTouchTargetWidth = 0
    private var mScrollerHandler: Handler? = null
    private var mScroller: Scroller? = null
    private var mDecorView: ViewGroup? = null
    private var mContentTarget: ViewGroup? = null
    private var mContentTargetParent: ViewGroup? = null
    private var mWindowTarget: ViewGroup? = null
    private var mWindowTargetParent: ViewGroup? = null
    private var mDecorContent: ViewGroup? = null
    private var mDecorContentParent: ViewGroup? = null
    private var mDrawerContent: LinearLayout? = null
    private var mVelocityTracker: VelocityTracker? = null
    private var mOnOpenDrawerCompleteListener: OnOpenDrawerCompleteListener? = null

    private var mLastMotionX = 0f
    private var mLastMotionY = 0f

    private var qx = 0f
    private var qy = 0f

    init {
        val dm = activity.resources.displayMetrics
        mTouchTargetWidth = dm.widthPixels / TOUCH_TARGET_WIDTH_DIP
        mScrollerHandler = Handler()
        mScroller = Scroller(activity, SmoothInterpolator())
        mSlideTarget = SLIDE_TARGET_WINDOW
        mDecorView = activity.window.decorView as ViewGroup
        mWindowTarget = mDecorView!!.getChildAt(0) as ViewGroup
        mWindowTargetParent = mWindowTarget!!.parent as ViewGroup
        mContentTarget = mDecorView!!.findViewById<View>(R.id.content) as ViewGroup
        mContentTargetParent = mContentTarget!!.parent as ViewGroup
        mDrawerContent = LinearLayout(context)
        mDrawerContent!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT)
        mDrawerContent!!.setBackgroundColor(Color.TRANSPARENT)
        reconfigureViewHierarchy()
        mDrawerContent!!.setPadding(0, 0, mTouchTargetWidth, 0)

    }

    class SmoothInterpolator : Interpolator {
        override fun getInterpolation(v: Float): Float {
            return (Math.pow(v.toDouble() - 1.0, 5.0) + 1.0f).toFloat()
        }
    }

    private fun reconfigureViewHierarchy() {
        if (mDecorView == null) {
            return
        }
        if (mDrawerContent != null) {
            removeView(mDrawerContent)
        }
        if (mDecorContent != null) {
            removeView(mDecorContent)
            mDecorContentParent!!.addView(mDecorContent)
            mDecorContent!!.setOnClickListener(null)
            mDecorContent!!.setBackgroundColor(Color.TRANSPARENT)
        }
        if (mAdded) {
            mDecorContentParent!!.removeView(this)
        }
        if (mSlideTarget == SLIDE_TARGET_CONTENT) {
            mDecorContent = mContentTarget
            mDecorContentParent = mContentTargetParent
        } else if (mSlideTarget == SLIDE_TARGET_WINDOW) {
            mDecorContent = mWindowTarget
            mDecorContentParent = mWindowTargetParent
        } else {
            throw IllegalArgumentException("Slide target must be one of SLIDE_TARGET_CONTENT or SLIDE_TARGET_WINDOW.")
        }
        (mDecorContent!!.parent as ViewGroup).removeView(mDecorContent)
        addView(mDrawerContent)
        addView(mDecorContent, LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT))
        mDecorContentParent!!.addView(this)
        mAdded = true
        mDecorContent!!.setBackgroundColor(Color.TRANSPARENT)
        mDecorContent!!.setOnClickListener { }
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val windowRect = Rect()
        mDecorView!!.getWindowVisibleDisplayFrame(windowRect)
        if (mSlideTarget == SLIDE_TARGET_WINDOW) {
            mDrawerContent!!.layout(left, top + windowRect.top, right, bottom)
        } else {
            mDrawerContent!!.layout(left, mDecorContent!!.top, right, bottom)
        }
        mDecorContent!!.layout(mDecorContent!!.left, mDecorContent!!.top, mDecorContent!!.left + right, bottom)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val vc = ViewConfiguration.get(context)
        val widthPixels = resources.displayMetrics.widthPixels
        val touchThreshold = widthPixels / TOUCH_HANDLE_WIDTH_DIP
        val hypo: Double
        val overcameSlop: Boolean
        if (!mDrawerEnabled) {
            return false
        }
        val x = ev.x
        val y = ev.y
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastMotionX = x
                mLastMotionY = y
                mGestureCurrentX = (ev.x + 0.5f).toInt()
                mGestureStartX = mGestureCurrentX
                mGestureCurrentY = (ev.y + 0.5f).toInt()
                mGestureStartY = mGestureCurrentY
                if (mGestureStartX < touchThreshold && !mDrawerOpened) {
                    mGestureStarted = true
                }
                if (mGestureStartX > widthPixels - mTouchTargetWidth && mDrawerOpened) {
                    mGestureStarted = true
                }
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = x - mLastMotionX
                val dy = y - mLastMotionY
                val xDiff = Math.abs(dx)
                val yDiff = Math.abs(y - mLastMotionY)
                if (xDiff > vc.scaledTouchSlop && Math.abs(xDiff) > Math.abs(yDiff) && Math.abs(dy) < Math.abs(vc.scaledTouchSlop / 1)) {
                } else {
                    return false
                }
                if (!mGestureStarted) {
                    return false
                }
                if (!mDrawerOpened && (ev.x < mGestureCurrentX || ev.x < mGestureStartX)) {
                    return false.also { mGestureStarted = it }
                }
                mGestureCurrentX = (ev.x + 0.5f).toInt()
                mGestureCurrentY = (ev.y + 0.5f).toInt()
                hypo = Math.hypot((mGestureCurrentX - mGestureStartX).toDouble(), (mGestureCurrentY - mGestureStartY).toDouble())
                overcameSlop = hypo > vc.scaledTouchSlop
                return overcameSlop
            }
            MotionEvent.ACTION_UP -> {
                if (mGestureStartX > widthPixels - mTouchTargetWidth && mDrawerOpened) {
                    closeDrawer()
                }
                mGestureStarted = false
                mGestureCurrentX = -1
                mGestureStartX = mGestureCurrentX
                mGestureCurrentY = -1
                mGestureStartY = mGestureCurrentY
                return false
            }
        }
        return false
    }

    @SuppressLint("Recycle")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val vc = ViewConfiguration.get(context)
        val widthPixels = resources.displayMetrics.widthPixels
        val deltaX = (event.x + 0.5f).toInt() - mGestureCurrentX
        val deltaY = (event.y + 0.5f).toInt() - mGestureCurrentY
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }
        mVelocityTracker!!.addMovement(event)
        mGestureCurrentX = (event.x + 0.5f).toInt()
        mGestureCurrentY = (event.y + 0.5f).toInt()
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                mDrawerMoving = true
                if (mDecorOffsetX + deltaX > widthPixels - mTouchTargetWidth) {
                    if (mDecorOffsetX != widthPixels - mTouchTargetWidth) {
                        mDrawerOpened = true
                        mDecorContent!!.offsetLeftAndRight(widthPixels - mTouchTargetWidth - mDecorOffsetX)
                        mDecorOffsetX = widthPixels - mTouchTargetWidth
                        invalidate()
                    }
                } else if (mDecorOffsetX + deltaX < 0) {
                    if (mDecorOffsetX != 0) {
                        mDrawerOpened = false
                        mDecorContent!!.offsetLeftAndRight(0 - mDecorContent!!.left)
                        mDecorOffsetX = 0
                        invalidate()
                    }
                } else {
                    if (mOnOpenDrawerCompleteListener != null && mOnOpenDrawerCompleteListener!!.onMoveRight()) {
                    } else {
                        mDecorContent!!.offsetLeftAndRight(deltaX)
                        mDecorOffsetX += deltaX
                        invalidate()
                        return false
                    }
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                qx = mGestureCurrentX.toFloat()
                qy = mGestureCurrentY.toFloat()
                mGestureStarted = false
                mDrawerMoving = false
                mVelocityTracker!!.computeCurrentVelocity(1000)
                if (Math.abs(mVelocityTracker!!.xVelocity) > vc.scaledMinimumFlingVelocity) {
                    if (mVelocityTracker!!.xVelocity > 0 && mDecorOffsetX > widthPixels / 5.0) {
                        mDrawerOpened = false
                        openDrawer()
                    } else {
                        mDrawerOpened = true
                        closeDrawer()
                    }
                } else {
                    if (mDecorOffsetX > widthPixels / 5.0) {
                        mDrawerOpened = false
                        openDrawer()
                    } else {
                        mDrawerOpened = true
                        closeDrawer()
                    }
                }
                return true
            }
        }
        return false
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (mDrawerOpened || mDrawerMoving) {
            canvas.save()
            canvas.translate(mDecorOffsetX.toFloat(), 0f)
            canvas.restore()
        }
    }

    fun setOnDrawerBackEnabled(enabled: Boolean) {
        mDrawerEnabled = enabled
    }

    fun isDrawerEnabled(): Boolean {
        return mDrawerEnabled
    }

    fun toggleDrawer(animate: Boolean) {
        if (!mDrawerOpened) {
            openDrawer(animate)
        } else {
            closeDrawer(animate)
        }
    }

    fun toggleDrawer() {
        toggleDrawer(true)
    }

    fun openDrawer(animate: Boolean) {
        if (mDrawerOpened || mDrawerMoving) {
            return
        }
        mDrawerMoving = true
        val widthPixels = resources.displayMetrics.widthPixels
        mScroller!!.startScroll(mDecorOffsetX, 0, widthPixels - mDecorOffsetX, 0, if (animate) SCROLL_DURATION else 0)
        mScrollerHandler!!.postDelayed(object : Runnable {
            override fun run() {
                val scrolling = mScroller!!.computeScrollOffset()
                mDecorContent!!.offsetLeftAndRight(mScroller!!.currX - mDecorOffsetX)
                mDecorOffsetX = mScroller!!.currX
                postInvalidate()
                if (!scrolling) {
                    mDrawerMoving = false
                    mDrawerOpened = true
                    mScrollerHandler!!.post {
                        enableDisableViewGroup(mDecorContent, false)
                        mOnOpenDrawerCompleteListener?.onOpenDrawerComplete()
                    }
                } else {
                    mScrollerHandler!!.postDelayed(this, 16)
                }
            }
        }, 16)
    }

    fun openDrawer() {
        openDrawer(true)
    }

    fun closeDrawer(animate: Boolean) {
        if (!mDrawerOpened || mDrawerMoving) {
            return
        }
        mDrawerMoving = true
        val widthPixels = resources.displayMetrics.widthPixels
        mScroller!!.startScroll(mDecorOffsetX, 0, -mDecorOffsetX, 0, if (animate) SCROLL_DURATION else 0)
        mScrollerHandler!!.postDelayed(object : Runnable {
            override fun run() {
                val scrolling = mScroller!!.computeScrollOffset()
                mDecorContent!!.offsetLeftAndRight(mScroller!!.currX - mDecorOffsetX)
                mDecorOffsetX = mScroller!!.currX
                postInvalidate()
                if (!scrolling) {
                    mDrawerMoving = false
                    mDrawerOpened = false
                    // if (mDrawerCallbacks != null) {
                    // mScrollerHandler.post(new Runnable() {
                    // @Override
                    // public void run() {
                    // enableDisableViewGroup(mDecorContent, true);
                    // mDrawerCallbacks.onDrawerClosed();
                    // }
                    // });
                    // }
                } else {
                    mScrollerHandler!!.postDelayed(this, 16)
                }
            }
        }, 16)
    }

    fun closeDrawer() {
        closeDrawer(true)
    }

    fun isDrawerOpened(): Boolean {
        return mDrawerOpened
    }

    fun isDrawerMoving(): Boolean {
        return mDrawerMoving
    }

    fun setOnOpenDrawerCompleteListener(mOnOpenDrawerCompleteListener: OnOpenDrawerCompleteListener?) {
        this.mOnOpenDrawerCompleteListener = mOnOpenDrawerCompleteListener
    }

    fun getSlideTarget(): Int {
        return mSlideTarget
    }

    fun setSlideTarget(slideTarget: Int) {
        if (mSlideTarget != slideTarget) {
            mSlideTarget = slideTarget
            reconfigureViewHierarchy()
        }
    }

    fun enableDisableViewGroup(viewGroup: ViewGroup?, enabled: Boolean) {
        val childCount = viewGroup!!.childCount
        for (i in 0 until childCount) {
            val view = viewGroup.getChildAt(i)
            if (view.isFocusable) {
                view.isEnabled = enabled
            }
            if (view is ViewGroup) {
                enableDisableViewGroup(view, enabled)
            } else if (view is ListView) {
                if (view.isFocusable()) {
                    view.setEnabled(enabled)
                }
                val listView = view
                val listChildCount = listView.childCount
                for (j in 0 until listChildCount) {
                    if (view.isFocusable()) {
                        listView.getChildAt(j).isEnabled = false
                    }
                }
            }
        }
    }

    interface OnOpenDrawerCompleteListener {
        fun onOpenDrawerComplete()
        fun onMoveRight(): Boolean
    }
}