package com.metes.worthit.core.domain.usecase

import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.repository.ItemsRepository
import com.metes.worthit.core.domain.repository.StorageRepository
import com.metes.worthit.core.domain.utils.Result
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class DeleteItemsUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val internalRepository: StorageRepository,
) {
    suspend operator fun invoke(itemIds: List<Int>, itemLocalImagePaths: Set<String?>): Result<Unit, Error> {
        return try {
            itemsRepository.deleteItems(itemIds)

            itemLocalImagePaths.forEach { path ->
                path?.let { internalRepository.deleteFile(path) }
            }

            Result.Success(Unit)
        } catch (c: CancellationException) {
            throw c
        } catch (_: Exception) {
            Result.Error(BusinessError.ItemFailedToDelete)
        }
    }
}
