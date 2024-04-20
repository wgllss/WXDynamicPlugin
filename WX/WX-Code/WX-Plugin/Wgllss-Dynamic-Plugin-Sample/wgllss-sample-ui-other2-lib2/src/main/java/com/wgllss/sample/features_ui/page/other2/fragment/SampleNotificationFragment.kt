package com.wgllss.sample.features_ui.page.other2.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.wgllss.core.units.ResourceUtils
import com.wgllss.core.units.SdkIntUtils
import com.wgllss.dynamic.plugin.manager.PluginManager
import com.wgllss.sample.feature_system.globle.Constants
import com.wgllss.sample.feature_system.globle.Constants.NOTIFICATION_LARGE_ICON_SIZE
import com.wgllss.sample.feature_system.globle.Constants.glideOptions
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.viewmodel.SampleActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SampleNotificationFragment : BasePluginFragment<SampleActivityViewModel>("fragment_notification_sample"), View.OnClickListener {
    private lateinit var btn_notifacaion1: TextView
    private lateinit var btn_notifacaion2: TextView

    private val mNormalChannelId = "渠道id" // 唯一性
    private val mNormalChannelName = "渠道名称"
    private lateinit var mManager: NotificationManager
    private lateinit var mBuilder: NotificationCompat.Builder
    private val mNormalNotificationId = 9001 // 通知id

    private val handler by lazy { MyHandler() }

    private val imgUrl = "https://pics0.baidu.com/feed/fcfaaf51f3deb48fa4ea6c867f33d9242cf578a5.jpeg"
    private val pendingIntent by lazy {
        val intent = PluginManager.instance.getStandardActivityIntent(
            requireActivity(),
            "classes_other2_res",
            "com.wgllss.sample.features_ui.page.other2.activity.Other3Activity",
            "com.wgllss.dynamic.sample.other2"
        )
        val pendingFlags = if (SdkIntUtils.isLollipop()) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        PendingIntent.getActivity(requireActivity(), 0, intent, pendingFlags)
    }


    override fun findView(context: Context, containerView: View) {
        btn_notifacaion1 = findViewByID("btn_notifacaion1")
        btn_notifacaion2 = findViewByID("btn_notifacaion2")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_notifacaion1.setOnClickListener(this)
        btn_notifacaion2.setOnClickListener(this)

        activity?.run {
            mManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        }
    }

    override fun onClick(v: View) {
        when (v) {
            btn_notifacaion1 -> {
                activity?.run {
                    // 适配8.0及以上 创建渠道
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(mNormalChannelId, mNormalChannelName, NotificationManager.IMPORTANCE_LOW).apply {
                            description = "描述2222"
                            setShowBadge(false) // 是否在桌面显示角标
                        }
                        mManager.createNotificationChannel(channel)
                    }
                    val intent = PluginManager.instance.getStandardActivityIntent(
                        this,
                        "classes_other2_res",
                        "com.wgllss.sample.features_ui.page.other2.activity.Other3Activity",
                        "com.wgllss.dynamic.sample.other2"
                    )
                    val pendingFlags = if (SdkIntUtils.isLollipop()) {
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    } else {
                        PendingIntent.FLAG_UPDATE_CURRENT
                    }
                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingFlags)
                    // 构建配置
                    mBuilder = NotificationCompat.Builder(this, mNormalChannelId)
                        .setContentTitle("普通通知111") // 标题
                        .setContentText("普通通知内容2222222222222") // 文本
                        .setSmallIcon(Constants.defaultImgID) // 小图标
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 7.0 设置优先级
                        .setContentIntent(pendingIntent) // 跳转配置
                        .setAutoCancel(true) // 是否自动消失（点击）or mManager.cancel(mNormalNotificationId)、cancelAll、setTimeoutAfter()
                    // 发起通知
                    mManager.notify(mNormalNotificationId, mBuilder.build())
                }
            }
            btn_notifacaion2 -> {
                activity?.run {
                    // 适配8.0及以上 创建渠道
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(mNormalChannelId + 1, mNormalChannelName, NotificationManager.IMPORTANCE_LOW).apply {
                            description = "描述2222"
                            setShowBadge(false) // 是否在桌面显示角标
                        }
                        mManager.createNotificationChannel(channel)
                    }
                    createBuildWithBitmap()
                }
            }
            else -> {

            }
        }
    }

    private inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> {
                    createBuildWithBitmap(msg.obj as Bitmap)
                }
                else -> {

                }
            }
        }
    }

    private fun createBuildWithBitmap(bitmap: Bitmap? = null) {
        activity?.run {
            // 构建配置
            mBuilder = NotificationCompat.Builder(this, mNormalChannelId + 1)
                .setContentTitle("普通通知3333") // 标题
                .setContentText("普通通知内容55555566666666666aaaaaaaaaaaa") // 文本
                .setSmallIcon(Constants.defaultImgID) // 小图标
            if (bitmap == null) {
                lifecycleScope.launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        Glide.with(this@run).applyDefaultRequestOptions(glideOptions)
                            .asBitmap()
                            .load(imgUrl)
                            .submit(NOTIFICATION_LARGE_ICON_SIZE, NOTIFICATION_LARGE_ICON_SIZE)
                            .get()
                    }
                    bitmap?.let {
                        handler.sendMessage(handler.obtainMessage(1, it))
                    }
                }
            } else {
                mBuilder.setLargeIcon(bitmap)
            }
            mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT) // 7.0 设置优先级
                .setContentIntent(pendingIntent) // 跳转配置
                .setAutoCancel(true) // 是否自动消失（点击）or mManager.cancel(mNormalNotificationId)、cancelAll、setTimeoutAfter()
            // 发起通知
            mManager.notify(mNormalNotificationId, mBuilder.build())
        }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, btn_notifacaion1, btn_notifacaion2)
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, btn_notifacaion1, btn_notifacaion2)
    }
}