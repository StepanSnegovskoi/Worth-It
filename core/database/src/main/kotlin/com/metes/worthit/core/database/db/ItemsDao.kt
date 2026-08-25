package com.metes.worthit.core.database.db

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.metes.worthit.core.database.entity.ItemDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Upsert
    suspend fun saveItem(item: ItemDbModel): Long

    @Query("SELECT * FROM items")
    fun observeItems(): Flow<List<ItemDbModel>>

    @Query("DELETE FROM items WHERE id = :itemId")
    suspend fun deleteItem(itemId: Int): Int

    @Query("SELECT * FROM items WHERE id = :itemId LIMIT 1")
    suspend fun getItemById(itemId: Int): ItemDbModel
}
