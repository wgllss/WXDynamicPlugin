package com.wgllss.dynamic.initializer

import android.content.Context
import android.content.Intent
import androidx.startup.Initializer
import com.wgllss.core.ex.parseErrorString
import com.wgllss.core.widget.CommonToast
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.HOME
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.MLK
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.dldir
import com.wgllss.dynamic.host.lib.download.DownLoadResult
import com.wgllss.dynamic.host.lib.download.DynamicDownloadPlugin
import com.wgllss.dynamic.host.lib.impl.WXDynamicLoader
import com.wgllss.dynamic.system.broadcast.BroadCastAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.text.StringBuilder

class InitHomeFirstInitialize : Initializer<Unit> {

    override fun create(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            WXDynamicLoader.instance.loader?.run {
                val face = WXDynamicLoader.instance.loader.getDownloadFace()
                val downloadHelp = DynamicDownloadPlugin(face)
                val ddir = dldir
                var flow: Flow<DownLoadResult>? = null
                var flowNew: Flow<Any?>? = null
                getMapDluImpl().forEach { (key, value) ->
                    val createFlow = flow {
                        emit(downloadHelp.initDynamicByKey(context, key, ddir, StringBuilder().append(value.first).append("_").append(value.second).toString()))
                    }
                    if (flow == null) {
                        flow = createFlow
                    } else {
                        if (flowNew == null) {
                            val f = flow!!.zip(createFlow) { it, it2 ->
                                it?.run {
                                    initDynamicRunTime(context, contentKey, fileAbsolutePath)
                                }
                                it2?.run {
                                    initDynamicRunTime(context, contentKey, fileAbsolutePath)
                                }
                            }
                            flowNew = f
                        } else {
                            val f = flowNew!!.zip(createFlow) { _, it2 ->
                                it2?.run {
                                    if (HOME == key) {
                                        initDynamicRunTime(context, MLK, fileAbsolutePath)
                                        initHomeCreate(context, javaClass.classLoader)
                                        context.sendBroadcast(Intent(BroadCastAction.DOWNLOAD_HOME_COMPLETE_ACTION))
                                    } else {
                                        initDynamicRunTime(context, contentKey, fileAbsolutePath)
                                    }
                                }
                            }
                            flowNew = f
                        }
                    }
                }
                flowNew?.flowOn(Dispatchers.IO)
                    ?.catch {
                        it.printStackTrace()
                        CommonToast.show(it.parseErrorString())
                    }?.collect()
            }
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}