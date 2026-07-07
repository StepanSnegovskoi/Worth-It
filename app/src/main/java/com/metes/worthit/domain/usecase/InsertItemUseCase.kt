package com.metes.worthit.domain.usecase

import com.metes.worthit.domain.entity.Item
import com.metes.worthit.domain.repository.ItemsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertItemUseCase @Inject constructor(
    private val repository: ItemsRepository
) {
    suspend operator fun invoke(
        name: String,
    ) {
        val item = Item(name = name)
        repository.insertItem(item)
    }
}
