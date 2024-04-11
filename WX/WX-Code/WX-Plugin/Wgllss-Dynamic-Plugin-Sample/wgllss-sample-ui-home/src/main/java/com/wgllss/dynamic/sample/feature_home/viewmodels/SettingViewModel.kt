package com.wgllss.dynamic.sample.feature_home.viewmodels
//
//import androidx.lifecycle.MutableLiveData
//import com.wgllss.core.viewmodel.BaseViewModel
//import com.wgllss.sample.feature_system.savestatus.MMKVHelp
//
//class SettingViewModel : BaseViewModel() {
//
//    val isOpenLockerUI by lazy { MutableLiveData<Boolean>() }
//
//    val isNotificationOpen by lazy { MutableLiveData<Boolean>() }
//
//    override fun start() {
//        isOpenLockerUI.postValue(MMKVHelp.isOpenLockerUI())
//    }
//
//    fun setLockerSwitch(isOpen: Boolean) {
//        isOpenLockerUI.postValue(isOpen)
//        MMKVHelp.setLockerSwitch(isOpen)
//    }
//
//    fun setNotificationOpen(isOpen: Boolean) {
//        isNotificationOpen.postValue(isOpen)
//    }
//
//}