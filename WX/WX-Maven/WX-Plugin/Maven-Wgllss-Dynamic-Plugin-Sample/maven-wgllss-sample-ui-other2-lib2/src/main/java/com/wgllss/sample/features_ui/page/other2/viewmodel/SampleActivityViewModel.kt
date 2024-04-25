package com.wgllss.sample.features_ui.page.other2.viewmodel

import android.content.Context
import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.wgllss.core.units.WLog
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.core.widget.CommonToast
import com.wgllss.dynamic.host.lib.download.DynamicDownloadPlugin
import com.wgllss.dynamic.host.lib.impl.WXDynamicLoader
import com.wgllss.nativex.MainActivity
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class SampleActivityViewModel : BaseViewModel() {

    val str by lazy { MutableLiveData(" 我这是示例数据") }
    val url by lazy { MutableLiveData("https://dingyue.ws.126.net/2024/0408/901a74abj00sbly7z0090d000gh00h6m.jpg") }


    override fun start() {
    }

    fun downloadSo(context: Context) {
        flowAsyncWorkOnViewModelScopeLaunch {
            flow {
                //todo so 一般比较大了，很难做到只有 40~50k左右，故不放在 版本控制里面去，需要单独业务区控制 下载完成之后 再调用
                //todo 如果启动就下载 ，需要耗费大量下载时间与资源，影响启动性能
                val face = WXDynamicLoader.instance.loader.getDownloadFace()
                DynamicDownloadPlugin(face).initDynamicPlugin(context, StringBuilder().append(face.getHostL()).append("/so/lib/").append(Build.CPU_ABI).append("/libnativex.so").toString(), "so", "libnativex.so").run {
                    System.load(fileAbsolutePath)
                }
                emit(0)
            }.onEach {
                errorMsgLiveData.postValue(MainActivity().stringFromJNI())
            }
        }
    }
}