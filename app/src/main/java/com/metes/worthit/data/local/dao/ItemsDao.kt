package com.metes.worthit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.metes.worthit.data.local.entity.ItemDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Insert
    suspend fun insertItem(item: ItemDbModel)

    @Query("SELECT * FROM items")
    fun observeItems(): Flow<List<ItemDbModel>>
}