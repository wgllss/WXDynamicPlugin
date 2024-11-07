package com.wgllss.dynamic.provider

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.wgllss.dynamic.host.lib.provider.WXHostContentProviderDelegate
import com.wgllss.sample.feature_system.room.help.RoomDBHelper

class TestContentProvider(val context: Context) : WXHostContentProviderDelegate {

    override fun onCreate(): Boolean {

        android.util.Log.e("TestContentProvider", "onCreate")
        return false
    }

    override fun query(uri: Uri?, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val db = RoomDBHelper.getReadableDatabase(context)
        try {
            db.beginTransaction()
//            return db.query("select * from collect_tab")
            selection?.let {
                return db.query(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
        android.util.Log.e("TestContentProvider", "query")
        return null
    }


    override fun getType(uri: Uri?): String? {
        android.util.Log.e("TestContentProvider", "getType")
        return null
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        android.util.Log.e("TestContentProvider", "insert")
        var insertUri: Uri? = null
        values?.let {
            val db = RoomDBHelper.getWritableDatabase(context)
            try {
                db.beginTransaction()
                insertUri = ContentUris.withAppendedId(uri!!, db.insert("collect_tab", SQLiteDatabase.CONFLICT_REPLACE, it))
                db.setTransactionSuccessful()
                context.contentResolver.notifyChange(uri!!, null)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.endTransaction()
            }
        }
        return insertUri
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<String>?): Int {
        android.util.Log.e("TestContentProvider", "delete ")
        val db = RoomDBHelper.getWritableDatabase(context)
        var result = 0
        try {
            db.beginTransaction()
            result = db.delete("collect_tab", selection, selectionArgs)
            db.setTransactionSuccessful()
            if (result > 0) {
                context.contentResolver.notifyChange(uri!!, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
        return result
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        android.util.Log.e("TestContentProvider", "update")
        var result = 0
        values?.let {
            val db = RoomDBHelper.getWritableDatabase(context)
            try {
                db.beginTransaction()
                result = db.update("collect_tab", SQLiteDatabase.CONFLICT_NONE, it, selection!!, selectionArgs!!)
                db.setTransactionSuccessful()
                if (result > 0) {
                    context.contentResolver.notifyChange(uri!!, null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.endTransaction()
            }
        }
        return result
    }
}