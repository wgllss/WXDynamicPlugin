package com.wgllss.sample.features_ui.page.other2.fragment

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.wgllss.core.units.ResourceUtils
import com.wgllss.dynamic.sample.other2.BuildConfig
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.dialog.MyDialog
import com.wgllss.sample.features_ui.page.other2.viewmodel.SampleActivityViewModel

class SampleDialogFragment : BasePluginFragment<SampleActivityViewModel>("fragment_dialog_sample"), View.OnClickListener {
    private lateinit var btn_dialog: TextView

    override fun findView(context: Context, containerView: View) {
        btn_dialog = findViewByID("btn_dialog")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_dialog.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btn_dialog -> {
                activity?.run {
                    MyDialog(this, getSkinResources(), getPluginResources(), "dialog_sample", BuildConfig.LIBRARY_PACKAGE_NAME).show()
                }
            }

            else -> {

            }
        }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, btn_dialog)
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, btn_dialog)
    }
}