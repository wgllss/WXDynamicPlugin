package com.wgllss.sample.feature_system.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wgllss.core.units.WLog
import com.wgllss.sample.feature_system.room.dao.CollectDao
import com.wgllss.sample.feature_system.room.help.RoomDBMigration
import com.wgllss.sample.feature_system.room.table.CollectTableBean

@Database(entities = [CollectTableBean::class], version = 1, exportSchema = false)
abstract class CollectDataBase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: CollectDataBase? = null
        fun getInstance(context: Context, roomDBMigration: RoomDBMigration): CollectDataBase {
            if (instance == null) {
                synchronized(CollectDataBase::class.java) {
                    if (instance == null) {
                        val builder = Room.databaseBuilder(context, CollectDataBase::class.java, "wx_sample_db")
                        val migrations = roomDBMigration.createMigration()
                        migrations?.takeIf {
                            it.isNotEmpty()
                        }?.let {
                            builder.addMigrations(*it)
                        }
                        builder.addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                WLog.e(this@Companion, "RoomDatabase onCreate")
                            }

                            override fun onOpen(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                                WLog.e(this@Companion, "RoomDatabase onOpen")
                            }

                            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                                super.onDestructiveMigration(db)
                                WLog.e(this@Companion, "RoomDatabase onDestructiveMigration")
                            }
                        })
                        instance = builder.build()
                    }
                }
            }
            return instance!!
        }
    }

    abstract fun collectDao(): CollectDao
}