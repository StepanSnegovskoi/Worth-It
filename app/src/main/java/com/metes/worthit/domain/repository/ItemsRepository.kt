package com.metes.worthit.domain.repository

import com.metes.worthit.domain.entity.Item
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    suspend fun insertItem(item: Item)
    fun observeItems(): Flow<List<Item>>
}