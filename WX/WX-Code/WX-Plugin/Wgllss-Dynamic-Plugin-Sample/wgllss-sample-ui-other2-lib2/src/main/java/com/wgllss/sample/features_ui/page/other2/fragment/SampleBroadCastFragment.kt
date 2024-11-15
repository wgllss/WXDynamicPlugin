package com.wgllss.sample.features_ui.page.other2.fragment

import android.content.*
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.TextView
import com.wgllss.core.units.ResourceUtils
import com.wgllss.dynamic.plugin.manager.PluginManager
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.broadcast.TestBroadCast
import com.wgllss.sample.features_ui.page.other2.viewmodel.SampleActivityViewModel

class SampleBroadCastFragment : BasePluginFragment<SampleActivityViewModel>("fragment_broadcast_sample"), View.OnClickListener {
    private lateinit var btn_send_broadcast: TextView
    private val testBroadCast by lazy { TestBroadCast() }

    override fun findView(context: Context, containerView: View) {
        btn_send_broadcast = findViewByID("btn_send_broadcast")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_send_broadcast.setOnClickListener(this)
        val intentFilter = IntentFilter("action_key")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) activity?.registerReceiver(testBroadCast, intentFilter, Context.RECEIVER_EXPORTED)
        else activity?.registerReceiver(testBroadCast, intentFilter)
    }

    override fun onClick(v: View) {
        when (v) {
            btn_send_broadcast -> {
                activity?.sendBroadcast(Intent("action_key"))
            }

            else -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.unregisterReceiver(testBroadCast)
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, btn_send_broadcast)
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, btn_send_broadcast)
    }
}