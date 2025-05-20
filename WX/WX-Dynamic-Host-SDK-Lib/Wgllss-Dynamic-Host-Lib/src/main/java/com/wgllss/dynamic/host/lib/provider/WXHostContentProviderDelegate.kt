package com.wgllss.dynamic.host.lib.provider

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

interface WXHostContentProviderDelegate {

    fun onCreate(): Boolean

    fun query(uri: Uri?, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor?

    fun getType(uri: Uri?): String?


    fun insert(uri: Uri?, values: ContentValues?): Uri?


    fun delete(uri: Uri?, selection: String?, selectionArgs: Array<String>?): Int


    fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int
}