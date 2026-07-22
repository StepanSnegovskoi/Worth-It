package com.metes.worthit.core.domain.usecase

import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveItemsUseCase @Inject constructor(
    private val repository: ItemsRepository
) {
    operator fun invoke(): Flow<List<Item>> {
        return repository.observeItems()
    }
}
