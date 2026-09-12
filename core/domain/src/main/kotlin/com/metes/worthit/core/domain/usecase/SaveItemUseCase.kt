package com.metes.worthit.core.domain.usecase

import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.repository.ItemsRepository
import com.metes.worthit.core.domain.repository.StorageRepository
import com.metes.worthit.core.domain.utils.Result
import com.metes.worthit.core.domain.utils.onError
import com.metes.worthit.core.domain.utils.onSuccess
import com.metes.worthit.core.domain.validator.ItemValidator
import kotlinx.coroutines.CancellationException
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

class SaveItemUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val internalRepository: StorageRepository,
    private val itemValidator: ItemValidator,
) {
    suspend operator fun invoke(
        itemId: Int? = null,
        name: String,
        price: String,
        currency: Currency,
        createdAt: Instant,
        dateOfPurchase: LocalDate,
        description: String,
        imageUriString: String?,
        originalImageLocalPath: String?,
    ): Result<Unit, List<Error>> {
        val validatorResult = itemValidator.validateAll(name, description, dateOfPurchase, price)

        if (validatorResult is Result.Error) {
            return Result.Error(validatorResult.data)
        }

        val validatedFields = (validatorResult as Result.Success).data

        var finalImagePath: String? = null

        if (imageUriString != null) {
            val saveImageResult = internalRepository.saveImage(imageUriString)
            if (saveImageResult is Result.Error) {
                return Result.Error(listOf(saveImageResult.data))
            }
            finalImagePath = (saveImageResult as Result.Success).data
        }

        return try {
            val item = Item(
                id = itemId ?: Item.DEFAULT_ID,
                name = validatedFields.name,
                price = validatedFields.price,
                currency = currency,
                createdAt = createdAt,
                dateOfPurchase = validatedFields.dateOfPurchase,
                description = validatedFields.description,
                imageLocalPath = finalImagePath
            )

            val isSaved = itemsRepository.saveItem(item)
            if (isSaved) {
                if (finalImagePath != originalImageLocalPath) {
                    originalImageLocalPath?.let { internalRepository.deleteFile(it) }
                }
                Result.Success(Unit)
            } else {
                cleanupNewImage(finalImagePath, originalImageLocalPath)
                Result.Error(listOf(BusinessError.ItemFailedToSave))
            }
        } catch (c: CancellationException) {
            throw c
        } catch (_: Exception) {
            cleanupNewImage(finalImagePath, originalImageLocalPath)
            Result.Error(listOf(BusinessError.ItemFailedToSave))
        }
    }

    private suspend fun cleanupNewImage(finalImagePath: String?, originalPath: String?) {
        if (finalImagePath != null && finalImagePath != originalPath) {
            internalRepository.deleteFile(finalImagePath)
        }
    }
}
