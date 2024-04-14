package com.wgllss.dynamic.provider

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.wgllss.dynamic.host.lib.provider.WXHostContentProviderDelegate

class TestContentProvider(val context: Context) : WXHostContentProviderDelegate {

    override fun onCreate(): Boolean {
        android.util.Log.e("TestContentProviderWX", "onCreate")
        return false
    }

    override fun query(uri: Uri?, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        android.util.Log.e("TestContentProviderWX", "query")
        return null
    }


    override fun getType(uri: Uri?): String? {
        android.util.Log.e("TestContentProviderWX", "getType")
        return null
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        android.util.Log.e("TestContentProviderWX", "insert")
        return null
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<String>?): Int {
        android.util.Log.e("TestContentProviderWX", "delete")
        return 0
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        android.util.Log.e("TestContentProviderWX", "update")
        return 0
    }
}