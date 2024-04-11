package com.wgllss.sample.feature_system.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collect_tab")
class CollectTableBean(
    @PrimaryKey @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER, defaultValue = "0") var id: Long,
    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT, defaultValue = "") val title: String,
    @ColumnInfo(name = "imgUrl", typeAffinity = ColumnInfo.TEXT, defaultValue = "") val imgUrl: String,
    @ColumnInfo(name = "createTime", typeAffinity = ColumnInfo.INTEGER, defaultValue = "") val createTime: Long
)