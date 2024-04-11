package com.wgllss.sample.feature_system.room.help

import androidx.room.migration.Migration

class RoomDBMigration {

    companion object {
        val instance by lazy { RoomDBMigration() }
    }

    fun createMigration(): Array<Migration> {
        val migrations = arrayListOf<Migration>()
        return migrations.toTypedArray()
    }
}