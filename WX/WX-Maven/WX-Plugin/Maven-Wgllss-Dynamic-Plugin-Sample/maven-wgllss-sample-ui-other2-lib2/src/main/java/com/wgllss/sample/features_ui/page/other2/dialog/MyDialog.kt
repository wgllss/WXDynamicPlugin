package com.wgllss.sample.features_ui.page.other2.dialog

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.button.MaterialButton
import com.wgllss.core.units.ResourceUtils
import com.wgllss.sample.features_ui.page.base.SkinContains

class MyDialog(
    context: Context,
    private val skinRes: Resources,
    private val resources: Resources,
    private val layoutName: String,
    private val packageName: String
) : Dialog(context, android.R.style.ThemeOverlay_Material_Dialog_Alert) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutID = resources.getIdentifier(layoutName, "layout", packageName)
        val xmlResourceParser = resources.getLayout(layoutID)
        val containerView = LayoutInflater.from(context).inflate(xmlResourceParser, null)
        setContentView(containerView)


        val id = resources.getIdentifier("btn_dialog_0", "id", packageName)
        val btn = containerView.findViewById<MaterialButton>(id).apply {
            setOnClickListener {
                dismiss()
            }
        }
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, btn)
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, btn)
    }
}