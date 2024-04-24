package com.wgllss.core.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.wgllss.core.units.AppGlobals
import java.util.*

class CommonToast {
    companion object {
        private val toastBackgroundId = -1
        private val toastTextColorId = -1
        private var lastDate: Date? = null

        // 提示时间间隔3秒钟
        private val toastPeriod = 3
        private var mToast: Toast? = null
        private var toastText: TextView? = null
        private val mHandler: Handler = Handler(Looper.getMainLooper())
        private val r = Runnable {
            if (mToast != null) {
                mToast!!.cancel()
                mToast = null // toast隐藏后，将其置为null
            }
            if (toastText != null) {
                toastText = null
            }
        }

        /**
         * 显示toast
         *
         * @param strContent      显示内容
         * @param widthRate       占屏幕宽度比例 0为 WRAP_CONTENT
         * @param heightRate      占屏幕高度比例 0为 WRAP_CONTENT
         * @param gravity         占屏幕位置
         * @param delayMillis     延迟多少毫秒隐藏tost
         * @param isInToastPeriod isInToastPeriod 在规定的时间间隔里内是否显示
         * @author :Atar
         * @createTime:2016-3-18上午10:19:13
         * @version:1.0.0
         * @modifyTime:
         * @modifyAuthor:
         * @description:
         */
        @JvmStatic
        fun show(context: Context, strContent: String?, widthRate: Int, heightRate: Int, gravity: Int, offy: Int, delayMillis: Long, isInToastPeriod: Boolean) {
            show(context, strContent, widthRate, heightRate, gravity, offy, delayMillis, isInToastPeriod, 1, 0)
        }

        /**
         * 显示toast
         *
         * @param strContent      显示内容
         * @param widthRate       占屏幕宽度比例 0为 WRAP_CONTENT
         * @param heightRate      占屏幕高度比例 0为 WRAP_CONTENT
         * @param gravity         占屏幕位置
         * @param delayMillis     延迟多少毫秒隐藏tost
         * @param isInToastPeriod isInToastPeriod 在规定的时间间隔里内是否显示
         * @param orientation     屏幕方向
         * @param rotation        旋转角度
         * @author :Atar
         * @createTime:2016-3-18上午10:19:13
         * @version:1.0.0
         * @modifyTime:
         * @modifyAuthor:
         * @description:
         */
        @JvmStatic
        fun show(context: Context, strContent: String?, widthRate: Int, heightRate: Int, gravity: Int, offy: Int, delayMillis: Long, isInToastPeriod: Boolean, orientation: Int, rotation: Int) {
            try {
                if (isInToastPeriod) {
                    return
                }
                mHandler!!.removeCallbacks(r)
                if (mToast == null || toastText == null) { // 只有mToast==null时才重新创建，否则只需更改提示文字
                    mToast = Toast(context)
                    val toastLayout = LinearLayout(context)
                    toastLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    toastLayout.orientation = LinearLayout.VERTICAL
                    toastLayout.gravity = Gravity.CENTER
                    toastText = TextView(context)
                    val size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.resources.displayMetrics).toInt()
                    var width = 0
                    var height = 0
                    if (orientation == 1) { // 1竖屏
                        val widthPixels = context.resources.displayMetrics.widthPixels
                        width = if (widthRate == 0) {
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        } else {
                            widthPixels / widthRate
                        }
                        height = if (heightRate == 0) {
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        } else {
                            widthPixels / heightRate
                        }
                        toastText!!.layoutParams = LinearLayout.LayoutParams(width, height)
                        toastLayout.rotation = rotation.toFloat()
                    } else {
                        val widthPixels = context.resources.displayMetrics.heightPixels
                        width = if (widthRate == 0) {
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        } else {
                            widthPixels / widthRate
                        }
                        height = if (heightRate == 0) {
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        } else {
                            widthPixels / heightRate
                        }
                        toastText!!.layoutParams = LinearLayout.LayoutParams(width, height)
                        toastLayout.rotation = rotation.toFloat()
                    }
                    toastText!!.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
                    toastText!!.setPadding(size, size, size, size)
                    toastText!!.gravity = Gravity.CENTER
                    if (toastBackgroundId != -1) {
                        toastLayout.setBackgroundDrawable(context.resources.getDrawable(toastBackgroundId))
                    } else {
                        setText(toastText!!)
                    }
                    if (toastTextColorId != -1) {
                        toastText!!.setTextColor(context.resources.getColor(toastTextColorId))
                    } else {
                        setText(toastText!!)
                    }
                    toastLayout.addView(toastText)
                    mToast!!.view = toastLayout
                    mToast!!.setGravity(gravity, 0, offy)
                    mToast!!.duration = Toast.LENGTH_SHORT
                }
                if (toastText != null) {
                    toastText!!.text = strContent
                }
                mHandler.postDelayed(r, delayMillis) // 延迟delayMillis耗秒隐藏toast
                try {
                    mToast!!.show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } catch (e: Exception) {
            }
        }

        @JvmStatic
        fun show(resID: Int) {
            show(AppGlobals.sApplication, AppGlobals.sApplication.resources.getString(resID), 0, 0, Gravity.CENTER, 0, 3000, false)
        }

        /**
         * 显示Toast
         *
         * @param strContent:提示肉容
         * @author :Atar
         * @createTime:2014-6-25上午10:23:59
         * @version:1.0.0
         * @modifyTime:
         * @modifyAuthor:
         * @description:
         */
        @JvmStatic
        fun show(strContent: String?) {
            show(AppGlobals.sApplication, strContent, 0, 0, Gravity.CENTER, 0, 3000, false)
        }

        @JvmStatic
        fun show(strContent: String?, dealyMills: Int) {
            show(AppGlobals.sApplication, strContent, 0, 0, Gravity.CENTER, 300, dealyMills.toLong(), false)
        }

        @JvmStatic
        fun show(strContent: String?, gravity: Int, offy: Int) {
            show(AppGlobals.sApplication, strContent, 0, 0, Gravity.CENTER, offy, 3000, false)
        }

        /**
         * 显示toast
         *
         * @param strContent  显示内容
         * @param widthRate   占屏幕宽度比例
         * @param heightRate  占屏幕高度比例
         * @param gravity     占屏幕位置
         * @param delayMillis 延迟多少毫秒隐藏tost
         * @author :Atar
         * @createTime:2016-3-18上午10:19:13
         * @version:1.0.0
         * @modifyTime:
         * @modifyAuthor:
         * @description:
         */
        @JvmStatic
        fun show(strContent: String?, widthRate: Int, heightRate: Int, gravity: Int, delayMillis: Long) {
            show(AppGlobals.sApplication, strContent, widthRate, heightRate, gravity, 300, delayMillis, false)
        }

        /**
         * 在时间间隔内不作提示
         *
         * @param strContent
         * @author :Atar
         * @createTime:2015-9-23下午3:33:03
         * @version:1.0.0
         * @modifyTime:
         * @modifyAuthor:
         * @description:
         */
        @JvmStatic
        fun showWhihPeriod(strContent: String?) {
            show(AppGlobals.sApplication, strContent, 0, 0, Gravity.CENTER, 0, 3000, isInToastPeriod())
        }

        /**
         * 在时间间隔内不作提示
         *
         * @param strContent
         * @author :Atar
         * @createTime:2015-9-23下午3:33:03
         * @version:1.0.0
         * @modifyTime:
         * @modifyAuthor:
         * @description:
         */
        fun showWhihPeriod(strContent: String?, delayMills: Int) {
            show(AppGlobals.sApplication, strContent, 0, 0, Gravity.CENTER, 0, delayMills.toLong(), isInToastPeriod())
        }

        /**
         * @param mTextView
         * @author :Atar
         * @createTime:2015-9-23下午3:33:13
         * @version:1.0.0
         * @modifyTime:
         * @modifyAuthor:
         * @description:
         */
        private fun setText(mTextView: TextView) {
            mTextView.setTextColor(Color.WHITE)
            val roundRadius = 8
            val fillColor = Color.parseColor("#90000000")
            val gd = GradientDrawable()
            gd.setColor(fillColor)
            gd.cornerRadius = roundRadius.toFloat()
            mTextView.setBackgroundDrawable(gd)
        }

        /**
         * 是否在提示时间间隔内
         *
         * @return
         * @author :Atar
         * @createTime:2014-11-12上午11:26:54
         * @version:1.0.0
         * @modifyTime:
         * @modifyAuthor:
         * @description:
         */
        fun isInToastPeriod(): Boolean {
            val curDate = Date(System.currentTimeMillis()) // 获取当前时间
            return if (curDate != null) {
                var lastTime: Long = 0
                lastTime = if (lastDate == null) {
                    0
                } else {
                    lastDate!!.time
                }
                val timeLong = curDate.time - lastTime
                if (timeLong < toastPeriod * 1000) {
                    true
                } else {
                    lastDate = curDate
                    false
                }
            } else {
                true
            }
        }

        fun dissMissToast() {
            if (mToast != null) {
                mToast!!.cancel()
                mToast = null // toast隐藏后，将其置为null
            }
            if (toastText != null) {
                toastText = null
            }
            mHandler?.removeCallbacksAndMessages(null)
        }
    }
}