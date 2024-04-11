package com.wgllss.sample.features_ui.page.other2.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wgllss.core.viewmodel.BaseViewModel

class SampleActivityViewModel : BaseViewModel() {

    val str by lazy { MutableLiveData(" 我这是示例数据") }
    val url by lazy { MutableLiveData("https://dingyue.ws.126.net/2024/0408/901a74abj00sbly7z0090d000gh00h6m.jpg") }

    override fun start() {
    }
}