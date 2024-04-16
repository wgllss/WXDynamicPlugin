package com.wgllss.sample.feature_system.room.help

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wgllss.sample.feature_system.room.CollectDataBase

object RoomDBHelper {


    fun getReadableDatabase(context: Context): SupportSQLiteDatabase {
        return CollectDataBase.getInstance(context, RoomDBMigration.instance).openHelper.readableDatabase
    }

    fun getWritableDatabase(context: Context): SupportSQLiteDatabase {
        return CollectDataBase.getInstance(context, RoomDBMigration.instance).openHelper.writableDatabase
    }
}