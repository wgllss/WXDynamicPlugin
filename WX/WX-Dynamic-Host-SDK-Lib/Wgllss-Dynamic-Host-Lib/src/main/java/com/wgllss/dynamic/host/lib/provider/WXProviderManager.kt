package com.wgllss.dynamic.host.lib.provider

class WXProviderManager private constructor() {

    private val map by lazy { HashMap<String, WXHostContentProviderDelegate>() }

    companion object {
        val instance by lazy { WXProviderManager() }
    }

    fun containsKey(key: String): WXHostContentProviderDelegate? {
        val newKey = key.replace("/", "")
        if (map.containsKey(newKey)) {
            return map[newKey]
        }
        return null
    }

    fun addContentProvider(providerDelegateName: WXHostContentProviderDelegate) {
        map[providerDelegateName.javaClass.name] = providerDelegateName
        providerDelegateName.onCreate()
    }
}