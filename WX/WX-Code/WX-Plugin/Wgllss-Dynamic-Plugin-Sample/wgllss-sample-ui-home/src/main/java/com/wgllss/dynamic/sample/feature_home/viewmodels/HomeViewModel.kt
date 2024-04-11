package com.wgllss.dynamic.sample.feature_home.viewmodels

import androidx.lifecycle.MutableLiveData
import com.wgllss.core.viewmodel.BaseViewModel

class HomeViewModel : BaseViewModel() {
    val mCurrentFragmentTAG by lazy { StringBuilder() }

    val lazyTabViewPager2 by lazy { MutableLiveData<Boolean>() }
    var isFirst = true

    fun lazyTabView() {
        lazyTabViewPager2.value = true
    }

    override fun start() {
    }
}