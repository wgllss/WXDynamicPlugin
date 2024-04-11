package com.wgllss.sample.features_ui.page.other2.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Resources
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.TextView
import com.wgllss.core.units.ResourceUtils
import com.wgllss.dynamic.plugin.manager.PluginManager
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.viewmodel.SampleActivityViewModel

class SampleServiceFragment : BasePluginFragment<SampleActivityViewModel>("fragment_service_sample"), View.OnClickListener {
    private lateinit var btn_start_service: TextView
    private lateinit var btn_start_service2: TextView
    private lateinit var btn_start_service3: TextView

    override fun findView(context: Context, containerView: View) {
        btn_start_service = findViewByID("btn_start_service")
        btn_start_service2 = findViewByID("btn_start_service2")
        btn_start_service3 = findViewByID("btn_start_service3")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_start_service.setOnClickListener(this)
        btn_start_service2.setOnClickListener(this)
        btn_start_service3.setOnClickListener(this)
    }

    private val sc: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.e("SampleServiceFragment", "onServiceConnected sc")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.e("SampleServiceFragment", "onServiceDisconnected sc")
        }
    }

    private val sc2: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.e("SampleServiceFragment", "onServiceConnected sc2")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.e("SampleServiceFragment", "onServiceDisconnected sc2")
        }
    }

    private val sc3: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.e("SampleServiceFragment", "onServiceConnected sc3")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.e("SampleServiceFragment", "onServiceDisconnected sc3")
        }
    }

    override fun onClick(v: View) {
        when (v) {
            btn_start_service -> {
                activity?.run {
                    PluginManager.instance.startPluginStartNotStickyService(
                        this, "classes_other2_dex",
                        "com.wgllss.sample.features_ui.page.other2.service.MyService",
                        "com.wgllss.dynamic.sample.other2", Intent().apply {
                            putExtra("service_key", "1321531")
                        }
                    )
                }
            }
            btn_start_service2 -> {
                activity?.run {
                    PluginManager.instance.startPluginStartNotStickyService(
                        this, "classes_other2_dex",
                        "com.wgllss.sample.features_ui.page.other2.service.MyService2",
                        "com.wgllss.dynamic.sample.other2", Intent().apply {
                            putExtra("service_key", "1321531")
                        }
                    )
                }
            }
            btn_start_service3 -> {
                activity?.run {
                    PluginManager.instance.startPluginStartStickyService(
                        this, "classes_other2_dex",
                        "com.wgllss.sample.features_ui.page.other2.service.MyService3",
                        "com.wgllss.dynamic.sample.other2", Intent().apply {
                            putExtra("service_key", "1321531")
                        }
                    )
                }
            }
            else -> {

            }
        }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, btn_start_service3, btn_start_service2, btn_start_service)
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, btn_start_service3, btn_start_service2, btn_start_service)
    }
}