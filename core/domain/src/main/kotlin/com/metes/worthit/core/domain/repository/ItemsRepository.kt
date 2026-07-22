package com.metes.worthit.core.domain.repository

import com.metes.worthit.core.domain.entity.Item
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    suspend fun insertItem(item: Item): Boolean
    fun observeItems(): Flow<List<Item>>
    suspend fun deleteItem(itemId: Int): Boolean
}