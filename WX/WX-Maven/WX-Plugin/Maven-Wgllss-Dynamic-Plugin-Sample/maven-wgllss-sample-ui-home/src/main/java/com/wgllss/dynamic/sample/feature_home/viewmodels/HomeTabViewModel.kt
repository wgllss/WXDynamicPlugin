package com.wgllss.dynamic.sample.feature_home.viewmodels

import androidx.lifecycle.MutableLiveData
import com.wgllss.core.units.AppGlobals
import com.wgllss.core.units.WLog
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.sample.data.NewsBean
import com.wgllss.sample.datasource.repository.NewsRepository
import kotlinx.coroutines.flow.onEach

class HomeTabViewModel : BaseViewModel() {

    private val newsRepositoryL by lazy { NewsRepository.getInstance(AppGlobals.sApplication) }


    val liveDataLoadSuccessCount by lazy { MutableLiveData(0) }

    var isClick = false

    var isLoadOffine = false

    val result by lazy { mutableMapOf<String, MutableLiveData<MutableList<NewsBean>>>() }
    private val pageNoMap by lazy { mutableMapOf<String, Int>() }
    private val isLoadingMore by lazy { mutableMapOf<String, Boolean>() }
    val enableLoadeMore by lazy { mutableMapOf<String, MutableLiveData<Boolean>>() }

    fun enableLoadMore(key: String) = !isLoadingMore[key]!! && enableLoadeMore[key]!!.value!!

    fun isLoadingMore(key: String) = pageNoMap[key] != 0

    override fun start() {
    }

    fun initKey(key: String) {
        result[key] = MutableLiveData<MutableList<NewsBean>>()
        isLoadingMore[key] = false
        enableLoadeMore[key] = MutableLiveData(true)
        pageNoMap[key] = 0
    }

    fun reset(key: String) {
        pageNoMap[key] = 0
    }


    fun getData(key: String) {
        isLoadingMore[key] = true
        isClick = false
        flowAsyncWorkOnViewModelScopeLaunch {
            val start = pageNoMap[key]!!
            val end = start + 10
            newsRepositoryL.getNetTabInfo(key, start, end).onEach {
                if (pageNoMap[key] == 0) {
                    WLog.e(this@HomeTabViewModel, key)
                    result[key]?.postValue(it)
                    isLoadOffine = false
                } else {
                    val list = result[key]?.value ?: mutableListOf()
                    list?.removeAt(list.size - 1)
                    list?.addAll(it)
                    result[key]?.postValue(list)
                }
                var c = liveDataLoadSuccessCount.value?.plus(1)
                liveDataLoadSuccessCount.postValue(c)
                enableLoadeMore[key]?.postValue(it.size == 10)
                if (it.size == 10)
                    pageNoMap[key] = pageNoMap[key]!!.plus(10)
                isLoadingMore[key] = false
            }
        }
    }

    fun addToCollection(it: NewsBean) {
        flowAsyncWorkOnViewModelScopeLaunch {
            newsRepositoryL.addToCollection(it).onEach {
                errorMsgLiveData.postValue(it)
            }
        }
    }
}