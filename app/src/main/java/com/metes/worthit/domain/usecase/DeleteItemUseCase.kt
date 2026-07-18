package com.metes.worthit.domain.usecase

import com.metes.worthit.domain.repository.ItemsRepository
import com.metes.worthit.domain.repository.LocalMediaRepository
import com.metes.worthit.domain.utils.Result
import dagger.Reusable
import kotlinx.coroutines.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

@Reusable
class DeleteItemUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val internalRepository: LocalMediaRepository,
) {
    suspend operator fun invoke(itemId: Int, itemLocalImagePath: String?): Result<Unit, Exception> {
        return try {
            val isDeletedFromDb = itemsRepository.deleteItem(itemId)
            if (isDeletedFromDb) {
                if (!itemLocalImagePath.isNullOrBlank()) {
                    internalRepository.deleteImage(itemLocalImagePath)
                }
                Result.Success(Unit)
            } else {
                Result.Error(IllegalStateException("Item with id $itemId could not be deleted from DB"))
            }
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
