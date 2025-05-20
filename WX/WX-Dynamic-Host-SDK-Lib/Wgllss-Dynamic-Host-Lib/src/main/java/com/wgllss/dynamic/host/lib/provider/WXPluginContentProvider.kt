package com.wgllss.dynamic.host.lib.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class WXPluginContentProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return false
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        uri.path?.let {
            return WXProviderManager.instance.containsKey(it)?.query(uri, projection, selection, selectionArgs, sortOrder)
        }
        return null
    }

    override fun getType(uri: Uri): String? {
        uri.path?.let {
            return WXProviderManager.instance.containsKey(it)?.getType(uri)
        }
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        uri.path?.let {
            return WXProviderManager.instance.containsKey(it)?.insert(uri, values)
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        uri.path?.let {
            return WXProviderManager.instance.containsKey(it)?.delete(uri, selection, selectionArgs) ?: 0
        }
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        uri.path?.let {
            return WXProviderManager.instance.containsKey(it)?.update(uri, values, selection, selectionArgs) ?: 0
        }
        return 0
    }
}