package com.wgllss.sample.features_ui.page.home.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.dynamic.host.lib.download.DownLoadResult
import com.wgllss.dynamic.host.lib.download.DynamicDownloadPlugin
import com.wgllss.dynamic.host.lib.impl.WXDynamicLoader
import com.wgllss.sample.datasource.SettingRepository
import com.wgllss.sample.datasource.SkinPluginBean
import com.wgllss.sample.feature_system.savestatus.MMKVHelp
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import java.lang.StringBuilder

class SettingViewModel : BaseViewModel() {

    val isOpenLockerUI by lazy { MutableLiveData<Boolean>() }

    val isNotificationOpen by lazy { MutableLiveData<Boolean>() }
    val downloadResult by lazy { MutableLiveData<DownLoadResult>() }
    val liveDataSkinList by lazy { MutableLiveData<MutableList<SkinPluginBean>>() }

    override fun start() {
        getSkins()
        isOpenLockerUI.postValue(MMKVHelp.isOpenLockerUI())
    }

    fun setLockerSwitch(isOpen: Boolean) {
        isOpenLockerUI.postValue(isOpen)
        MMKVHelp.setLockerSwitch(isOpen)
    }

    fun setNotificationOpen(isOpen: Boolean) {
        isNotificationOpen.postValue(isOpen)
    }

    private fun getSkins() {
        flowAsyncWorkOnViewModelScopeLaunch {
            SettingRepository.instance.getSkinConfig().onEach {
                liveDataSkinList.postValue(it)
            }
        }
    }

    fun downloadSkin(context: Context, skinPluginBean: SkinPluginBean) {
        flowAsyncWorkOnViewModelScopeLaunch {
            flow {
                val face = WXDynamicLoader.instance.loader.getDownloadFace()
                emit(DynamicDownloadPlugin(face).initDynamicPlugin(context, StringBuilder().append(face.getHostL()).append("/skins/").append(skinPluginBean.skinUrl).toString(), "skin", skinPluginBean.dlfn))
            }.onEach {
                downloadResult.postValue(it)
            }
        }
    }
}