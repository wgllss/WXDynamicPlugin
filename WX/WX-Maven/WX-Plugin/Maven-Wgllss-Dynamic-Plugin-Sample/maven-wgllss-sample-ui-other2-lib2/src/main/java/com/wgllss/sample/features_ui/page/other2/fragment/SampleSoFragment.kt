package com.wgllss.sample.features_ui.page.other2.fragment

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.wgllss.core.units.ResourceUtils
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.viewmodel.SampleActivityViewModel

class SampleSoFragment : BasePluginFragment<SampleActivityViewModel>("fragment_so_sample"), View.OnClickListener {
    private lateinit var btn_so: TextView

    override fun findView(context: Context, containerView: View) {
        btn_so = findViewByID("btn_so")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_so.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btn_so -> {
                activity?.run {
                    viewModel.downloadSo(this)
                }
            }

            else -> {

            }
        }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, btn_so)
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, btn_so)
    }
}