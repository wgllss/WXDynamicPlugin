package com.wgllss.sample.datasource

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wgllss.core.http.HttpUrlConnectionRequest
import com.wgllss.dynamic.host.lib.impl.WXDynamicLoader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.StringBuilder

class SettingRepository private constructor() {

    companion object {
        val instance by lazy { SettingRepository() }
    }

    suspend fun getSkinConfig(): Flow<MutableList<SkinPluginBean>> = flow {
        val face = WXDynamicLoader.instance.loader.getDownloadFace()
        val json = HttpUrlConnectionRequest.getServerJson(StringBuilder().append(face.getHostL()).append("WXDynamicPlugin").append("/skins/skins.txt").toString())
        val list = Gson().fromJson<MutableList<SkinPluginBean>>(json, object : TypeToken<MutableList<SkinPluginBean>>() {}.type)
        emit(list)
    }
}