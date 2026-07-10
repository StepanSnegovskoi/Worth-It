package com.metes.worthit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.metes.worthit.data.local.entity.ItemDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemDbModel): Long

    @Query("SELECT * FROM items")
    fun observeItems(): Flow<List<ItemDbModel>>
}