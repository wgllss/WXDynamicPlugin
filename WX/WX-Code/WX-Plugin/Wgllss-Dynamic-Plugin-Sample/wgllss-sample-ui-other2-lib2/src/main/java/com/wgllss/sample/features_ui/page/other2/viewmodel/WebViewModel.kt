package com.wgllss.sample.features_ui.page.other2.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wgllss.core.units.AppGlobals
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.sample.datasource.repository.NewsRepository
import kotlinx.coroutines.flow.onEach

class WebViewModel : BaseViewModel() {

    private val newsRepositoryL by lazy { NewsRepository.getInstance(AppGlobals.sApplication) }
    val livedataHtml by lazy { MutableLiveData<String>() }


    override fun start() {
    }

    fun getNewsDetailInfo(url: String, fileName: String) {
        flowAsyncWorkOnViewModelScopeLaunch {
            newsRepositoryL.getNewsDetailInfo(url, fileName)
                .onEach {
                    livedataHtml.postValue(it)
                }
        }
    }
}