package com.wgllss.dynamic.plugin.manager

import android.content.res.Resources
import com.wgllss.core.units.WLog
import java.util.concurrent.ConcurrentHashMap

object PluginResource {
    private val mapLayout by lazy { ConcurrentHashMap<String, Resources>() }

    fun getPluginResources(contentKey: String): Resources {
        WLog.e(this, "contentKey: $contentKey")
        return if (mapLayout.containsKey(contentKey) && mapLayout[contentKey] != null) {
            mapLayout[contentKey]!!
        } else {
            val res = PluginManager.instance.getPluginResources(contentKey)
            mapLayout[contentKey] = res
            res
        }
    }

    fun getSkinResources(): Resources {
        return PluginManager.instance.getPluginSkinResources()
    }

    fun getWebRes(): Resources {
        return PluginManager.instance.getWebRes()
    }
}