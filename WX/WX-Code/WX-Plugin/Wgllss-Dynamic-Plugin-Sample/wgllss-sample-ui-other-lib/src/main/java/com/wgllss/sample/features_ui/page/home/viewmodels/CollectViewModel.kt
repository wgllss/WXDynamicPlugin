package com.wgllss.sample.features_ui.page.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wgllss.core.units.AppGlobals
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.sample.datasource.repository.NewsRepository
import com.wgllss.sample.feature_system.room.table.CollectTableBean
import kotlinx.coroutines.flow.onEach

class CollectViewModel : BaseViewModel() {
    private val newsRepositoryL by lazy { NewsRepository.getInstance(AppGlobals.sApplication) }
    lateinit var liveData: LiveData<MutableList<CollectTableBean>>
    val isInitSuccess by lazy { MutableLiveData<Boolean>() }

    override fun start() {
        flowAsyncWorkOnViewModelScopeLaunch {
            newsRepositoryL.getAllList().onEach {
                liveData = it
                isInitSuccess.postValue(true)
            }
        }
    }

    fun deleteFromId(id: Long) {
        flowAsyncWorkOnViewModelScopeLaunch {
            newsRepositoryL.deleteFromId(id)
        }
    }
}