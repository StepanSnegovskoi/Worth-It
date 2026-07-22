package com.metes.worthit.core.database.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.metes.worthit.core.database.entity.ItemDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemDbModel): Long

    @Query("SELECT * FROM items")
    fun observeItems(): Flow<List<ItemDbModel>>

    @Query("DELETE FROM items WHERE id = :itemId")
    suspend fun deleteItem(itemId: Int): Int
}