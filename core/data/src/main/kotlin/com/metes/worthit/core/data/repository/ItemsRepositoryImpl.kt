package com.metes.worthit.core.data.repository

import com.metes.worthit.core.common.IoDispatcher
import com.metes.worthit.core.database.db.ItemsDao
import com.metes.worthit.core.database.entity.toDbModel
import com.metes.worthit.core.database.entity.toEntities
import com.metes.worthit.core.database.entity.toEntity
import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.repository.ItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ItemsRepositoryImpl @Inject constructor(
    private val dao: ItemsDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ItemsRepository {

    override suspend fun saveItem(item: Item): Boolean = withContext(ioDispatcher) {
        val dbModel = item.toDbModel()
        dao.saveItem(dbModel)
        return@withContext true
    }

    override fun observeItems(): Flow<List<Item>> {
        return dao.observeItems().map {
            it.toEntities()
        }
    }

    override suspend fun deleteItem(itemId: Int): Boolean = withContext(ioDispatcher) {
        val id = dao.deleteItem(itemId)
        return@withContext id > 0
    }

    override suspend fun deleteItems(itemIds: List<Int>)= withContext<Unit>(ioDispatcher) {
        dao.deleteItems(itemIds)
    }

    override suspend fun getItemById(itemId: Int): Item = withContext(ioDispatcher) {
        val item = dao.getItemById(itemId)
        return@withContext item.toEntity()
    }
}
