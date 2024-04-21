package com.wgllss.sample.features_ui.page.other2.activity

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.wgllss.core.ex.finishActivity
import com.wgllss.core.ex.setFramgment
import com.wgllss.core.units.ResourceUtils
import com.wgllss.sample.features_ui.page.base.BasePluginActivity
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.fragment.*
import com.wgllss.sample.features_ui.page.other2.viewmodel.Other2ViewModel

class Other2Activity : BasePluginActivity<Other2ViewModel>("activity_other2") {
    private lateinit var txt_activity_title: TextView
    private lateinit var view_title_bar: View
    private lateinit var layout_content: View
    private lateinit var img_back: ImageView

    override fun initControl(savedInstanceState: Bundle?) {
        super.initControl(savedInstanceState)
        txt_activity_title = findViewById(getPluginID("txt_activity_title"))
        view_title_bar = findViewById(getPluginID("view_title_bar"))
        img_back = findViewById(getPluginID("img_back"))
        layout_content = findViewById(getPluginID("layout_content"))
        window.setBackgroundDrawable(null)
    }

    override fun bindEvent() {
        super.bindEvent()
        img_back.setOnClickListener {
            activity.finishActivity()
        }
    }

    override fun initValue() {
        val actionType = intent?.getIntExtra("action_type", 0)
        val titestr = intent?.getStringExtra("itemName")
        txt_activity_title.text = "示例(${titestr})"
        val fragment = when (actionType) {
            0 -> SampleActivityFragment()
            1 -> SampleServiceFragment()
            2 -> SampleBroadCastFragment()
            3 -> SampleContentProviderFragment()
            4 -> SampleNotificationFragment()
            6 -> SampleSoFragment()
            7 -> SampleDialogFragment()
            else -> {
                null
            }
        }
        fragment?.run {
            this@Other2Activity.activity?.setFramgment(this, getPluginID("layout_content"))
        }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.run {
            setImageDrawable(skinRes, "ic_baseline_arrow_back_24", SkinContains.packageName, img_back)
            setBackgroundColor(skinRes, "colorBackground", SkinContains.packageName, layout_content)
            setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, view_title_bar)
            setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, txt_activity_title)

        }
    }
}