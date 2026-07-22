package com.metes.worthit.core.domain.usecase

import com.metes.worthit.core.domain.repository.ItemsRepository
import com.metes.worthit.core.domain.repository.LocalMediaRepository
import com.metes.worthit.core.domain.utils.Result
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val internalRepository: LocalMediaRepository,
) {
    suspend operator fun invoke(itemId: Int, itemLocalImagePath: String?): Result<Unit, Exception> {
        return try {
            val isDeletedFromDb = itemsRepository.deleteItem(itemId)

            if (!isDeletedFromDb) {
                return Result.Error(IllegalStateException("Item with id $itemId could not be deleted from DB"))
            }

            if (!itemLocalImagePath.isNullOrBlank()) {
                runCatching {
                    internalRepository.deleteImage(itemLocalImagePath)
                }.onFailure { e ->
                    e.printStackTrace()
                }
            }

            Result.Success(Unit)
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
