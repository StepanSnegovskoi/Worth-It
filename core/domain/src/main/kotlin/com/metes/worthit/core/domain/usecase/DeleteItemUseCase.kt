package com.metes.worthit.core.domain.usecase

import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.repository.ItemsRepository
import com.metes.worthit.core.domain.repository.StorageRepository
import com.metes.worthit.core.domain.utils.Result
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val internalRepository: StorageRepository,
) {
    suspend operator fun invoke(itemId: Int, itemLocalImagePath: String?): Result<Unit, Error> {
        return try {
            val isDeletedFromDb = itemsRepository.deleteItem(itemId)

            if (!isDeletedFromDb) {
                return Result.Error(BusinessError.ItemFailedToDelete)
            }

            if (!itemLocalImagePath.isNullOrBlank()) {
                internalRepository.deleteFile(itemLocalImagePath)
            }

            Result.Success(Unit)
        } catch (c: CancellationException) {
            throw c
        } catch (_: Exception) {
            Result.Error(BusinessError.ItemFailedToDelete)
        }
    }
}
