package com.metes.worthit.domain.usecase

import com.metes.worthit.domain.entity.Item
import com.metes.worthit.domain.repository.ItemsRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Reusable
class ObserveItemsUseCase @Inject constructor(
    private val repository: ItemsRepository
) {
    operator fun invoke(): Flow<List<Item>> {
        return repository.observeItems()
    }
}
