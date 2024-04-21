package com.wgllss.sample.features_ui.page.other2.web

import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi

class ImplWebViewClient : WebViewClient() {


    private val strOfflineResources by lazy { "" }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest?): WebResourceResponse? {
        val url = request?.url.toString()
        val lastSlash: Int = url.lastIndexOf("/")
        if (lastSlash != -1) {
            val suffix: String = url.substring(lastSlash + 1)
            if (suffix.endsWith(".css")) {
                if (strOfflineResources.contains(suffix)) {
                    val mimeType = "text/css"
                    val offlineRes = "css/"
                    val inputs = view.context.assets.open("$offlineRes$suffix")
                    return WebResourceResponse(mimeType, "UTF-8", inputs)
                } else {
                    android.util.Log.e("ImplWebViewClient", "request css :${url}")
                }
            }
            if (suffix.endsWith(".js")) {
                if (strOfflineResources.contains(suffix)) {
                    val mimeType = "application/x-javascript"
                    val offlineRes = "js/"
                    val inputs = view.context.assets.open("$offlineRes$suffix")
                    return WebResourceResponse(mimeType, "UTF-8", inputs)
                } else {
                    android.util.Log.e("ImplWebViewClient", "request js :${url}")
                }
            }
        }
        return super.shouldInterceptRequest(view, request)
    }
}