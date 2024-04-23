package com.wgllss.sample.features_ui.page.home.viewmodels

import androidx.lifecycle.MutableLiveData
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.sample.data.SampleItemBean

class SampleViewModel : BaseViewModel() {
    val sampleList by lazy { MutableLiveData<MutableList<SampleItemBean>>() }
    override fun start() {
        sampleList.postValue(
            mutableListOf(
                SampleItemBean(0, "Activity"),
                SampleItemBean(1, "Service"),
                SampleItemBean(2, "BroadcastReceiver"),
                SampleItemBean(3, "ContentProvider"),
                SampleItemBean(4, "Notification"),
                SampleItemBean(5, "WebView"),
                SampleItemBean(6, "SO加载"),
                SampleItemBean(7, "Dialog"),
                SampleItemBean(8, "视频")
            )
        )
    }
}