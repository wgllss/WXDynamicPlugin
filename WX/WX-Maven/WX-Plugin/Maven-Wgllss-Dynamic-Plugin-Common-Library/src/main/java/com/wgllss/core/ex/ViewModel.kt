package com.wgllss.core.ex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun <T> ViewModel.flowAsyncWorkOnLaunch(flowAsyncWork: suspend () -> Flow<T>) {
    viewModelScope.launch {
        flowAsyncWork.invoke().flowOnIOAndCatchAAndCollect()
    }
}



