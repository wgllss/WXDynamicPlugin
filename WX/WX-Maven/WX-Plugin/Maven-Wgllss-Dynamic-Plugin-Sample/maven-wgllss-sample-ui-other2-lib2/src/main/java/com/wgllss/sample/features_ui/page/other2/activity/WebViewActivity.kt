package com.wgllss.sample.features_ui.page.other2.activity

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.wgllss.core.ex.finishActivity
import com.wgllss.core.units.ResourceUtils
import com.wgllss.sample.features_ui.page.base.BasePluginActivity
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.viewmodel.WebViewModel
import com.wgllss.sample.features_ui.page.other2.web.ImplWebViewClient
class WebViewActivity : BasePluginActivity<WebViewModel>("activity_webview") {
    private lateinit var txt_activity_title: TextView
    private lateinit var view_title_bar: View
    private lateinit var webview: WebView
    private lateinit var img_back: ImageView

    override fun initControl(savedInstanceState: Bundle?) {
        super.initControl(savedInstanceState)
        txt_activity_title = findViewById(getPluginID("txt_activity_title"))
        view_title_bar = findViewById(getPluginID("view_title_bar"))
        img_back = findViewById(getPluginID("img_back"))
        webview = findViewById(getPluginID("webview"))
        window.setBackgroundDrawable(null)
    }

    override fun bindEvent() {
        super.bindEvent()
        img_back.setOnClickListener {
            activity.finishActivity()
        }
        viewModel.livedataHtml.observe(activity) {
            android.util.Log.e("WebViewActivity ", "html:${it}")
            webview.loadUrl(it)
//            webview.loadData(it, "text/html", "UTF-8")
        }
    }

    override fun initValue() {
        webview.apply {
            settings.apply {
                defaultTextEncodingName = "UTF-8"
                allowFileAccess = true
                allowContentAccess = true
                cacheMode = WebSettings.LOAD_NO_CACHE
                javaScriptEnabled = true
                domStorageEnabled = true
                setTextSize(WebSettings.TextSize.LARGEST)
                layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }
            }
            webViewClient = ImplWebViewClient()
        }
        val url = intent.getStringExtra("web_url_key") ?: ""
        val docid = intent.getStringExtra("docid_key") ?: ""
        val title = intent.getStringExtra("title_key") ?: ""

        txt_activity_title.text = if (title.length > 15) {
            title.substring(0, 15)
        } else title
        viewModel.getNewsDetailInfo(url, docid)
    }


    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.run {
            setImageDrawable(skinRes, "ic_baseline_arrow_back_24", SkinContains.packageName, img_back)
            setBackgroundColor(skinRes, "colorBackground", SkinContains.packageName, webview)
            setBackgroundColor(skinRes, "colorPrimary", SkinContains.packageName, view_title_bar)
            setTextColor(skinRes, "colorOnPrimary", SkinContains.packageName, txt_activity_title)
        }
    }
}