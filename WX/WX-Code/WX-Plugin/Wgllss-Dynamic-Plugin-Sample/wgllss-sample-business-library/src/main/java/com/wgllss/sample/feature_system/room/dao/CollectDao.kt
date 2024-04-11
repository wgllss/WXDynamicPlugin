package com.wgllss.sample.feature_system.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wgllss.sample.feature_system.room.table.CollectTableBean

@Dao
interface CollectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollectBean(collectTableBean: CollectTableBean)

    //ASC 默认值，从小到大，升序排列 DESC 从大到小，降序排列
    @Query("SELECT * FROM collect_tab ORDER BY createTime ASC")
    fun getList(): LiveData<MutableList<CollectTableBean>>

    @Query("SELECT COUNT(*) FROM collect_tab WHERE id = :uuID")
    fun queryByUUID(uuID: Long): Int

    @Query("DELETE FROM collect_tab WHERE id =:id")
    fun deleteFromID(id: Long)
}