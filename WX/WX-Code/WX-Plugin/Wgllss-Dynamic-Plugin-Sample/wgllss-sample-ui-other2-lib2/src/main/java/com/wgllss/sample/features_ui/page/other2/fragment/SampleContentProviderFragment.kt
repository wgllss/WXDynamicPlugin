package com.wgllss.sample.features_ui.page.other2.fragment

import android.content.Context
import android.content.res.Resources
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.wgllss.core.units.ResourceUtils
import com.wgllss.sample.features_ui.page.base.BasePluginFragment
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.viewmodel.SampleActivityViewModel

class SampleContentProviderFragment : BasePluginFragment<SampleActivityViewModel>("fragment_provider_sample"), View.OnClickListener {
    private lateinit var btn_query: TextView

    private val content_authority: String = "com.wgllss.dynamic.host.sample.wx.dynamic.plugin.author"
    private val base_content_uri = Uri.parse("content://$content_authority")

    private val uri = base_content_uri.buildUpon().appendPath("com.wgllss.dynamic.provider.TestContentProvider").build()

    override fun findView(context: Context, containerView: View) {
        btn_query = findViewByID("btn_query")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_query.setOnClickListener(this)
        activity?.run {
            contentResolver.registerContentObserver(uri, true, object : ContentObserver(Handler()) {
                override fun onChange(selfChange: Boolean, uri: Uri?) {
                    super.onChange(selfChange, uri)
                    Toast.makeText(this@run, uri.toString() + " onChange", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    override fun onClick(v: View) {
        when (v) {
            btn_query -> {
                activity?.run {
                    val cursor = contentResolver.query(uri, null, null, null, null)
                    val count = cursor?.count ?: 0
                    if (count > 0) {

                    }
                    cursor?.close()
                }
            }
            else -> {

            }
        }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, btn_query)
        ResourceUtils.setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, btn_query)
    }
}