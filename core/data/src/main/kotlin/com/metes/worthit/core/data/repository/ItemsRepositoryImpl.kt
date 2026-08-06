package com.metes.worthit.core.data.repository

import com.metes.worthit.core.common.DispatcherProvider
import com.metes.worthit.core.database.db.ItemsDao
import com.metes.worthit.core.database.entity.toDbModel
import com.metes.worthit.core.database.entity.toEntities
import com.metes.worthit.core.database.entity.toEntity
import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsRepositoryImpl @Inject constructor(
    private val dao: ItemsDao,
    private val dispatcherProvider: DispatcherProvider,
) : ItemsRepository {

    override suspend fun saveItem(item: Item): Boolean = withContext(dispatcherProvider.io) {
        val dbModel = item.toDbModel()
        dao.saveItem(dbModel)
        return@withContext true
    }

    override fun observeItems(): Flow<List<Item>> {
        return dao.observeItems().map {
            it.toEntities()
        }
    }

    override suspend fun deleteItem(itemId: Int): Boolean = withContext(dispatcherProvider.io) {
        val id = dao.deleteItem(itemId)
        return@withContext id > 0
    }

    override suspend fun getItemById(itemId: Int): Item = withContext(dispatcherProvider.io) {
        val item = dao.getItemById(itemId)
        return@withContext item.toEntity()
    }
}
