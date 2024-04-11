package com.wgllss.sample.features_ui.page.other2.fragment

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.wgllss.core.ex.loadUrl
import com.wgllss.core.units.ResourceUtils
import com.wgllss.dynamic.plugin.manager.PluginManager
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.viewmodel.SampleActivityViewModel

class SampleActivityFragment : BasePluginFragment<SampleActivityViewModel>("fragment_activity_sample") {
    private lateinit var img_url: ImageView;
    private lateinit var txt_test: TextView
    private lateinit var btn_jump: TextView

    override fun findView(context: Context, containerView: View) {
        img_url = findViewByID("img_url")
        txt_test = findViewByID("txt_test")
        btn_jump = findViewByID("btn_jump")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_jump.setOnClickListener {
            activity?.run {
                PluginManager.instance.startStandardActivity(
                    this, "classes_other2_res",
                    "com.wgllss.sample.features_ui.page.other2.activity.Other3Activity",
                    "com.wgllss.dynamic.sample.other2"
                )
            }
        }
        viewModel.run {
            str.observe(viewLifecycleOwner) {
                txt_test.text = it
            }
            url.observe(viewLifecycleOwner) {
                img_url.loadUrl(it)
            }
        }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, txt_test)
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, btn_jump)
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, btn_jump)
    }
}