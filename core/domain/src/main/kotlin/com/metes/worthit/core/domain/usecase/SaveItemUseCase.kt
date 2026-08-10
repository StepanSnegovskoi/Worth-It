package com.metes.worthit.core.domain.usecase

import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.error.UnexpectedError
import com.metes.worthit.core.domain.repository.ItemsRepository
import com.metes.worthit.core.domain.repository.StorageRepository
import com.metes.worthit.core.domain.utils.Result
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
        price: String?,
        currency: Currency,
        createdAt: Instant,
        dateOfPurchase: LocalDate,
        description: String,
        imageUriString: String?,
        originalImageLocalPath: String?,
    ): Result<Unit, List<Error>> {
        val validatorResult = itemValidator.validateAll(name, dateOfPurchase, price)

        if (validatorResult is Result.Error) {
            return Result.Error(validatorResult.data)
        }

        val validatedFields = (validatorResult as Result.Success).data

        val finalImagePath = imageUriString?.let {
            when (val imageResult = internalRepository.saveImage(imageUriString)) {
                is Result.Error -> return Result.Error(listOf(imageResult.data))
                is Result.Success -> imageResult.data
            }
        }

        val item = Item(
            id = itemId ?: Item.DEFAULT_ID,
            name = validatedFields.name,
            price = validatedFields.price,
            currency = currency,
            createdAt = createdAt,
            dateOfPurchase = validatedFields.dateOfPurchase,
            description = description,
            imageLocalPath = finalImagePath
        )

        val isImageChanged = finalImagePath != originalImageLocalPath

        val saveResult = try {
            if (itemsRepository.saveItem(item)) {
                Result.Success(Unit)
            } else {
                Result.Error(listOf(BusinessError.ItemFailedToSave))
            }
        } catch (c: CancellationException) {
            throw c
        } catch (_: Exception) {
            Result.Error(listOf(BusinessError.ItemFailedToSave))
        }

        if (isImageChanged) {
            when (saveResult) {
                is Result.Error<*> -> {
                    finalImagePath?.let { internalRepository.deleteFile(it) }
                }

                is Result.Success<*> -> {
                    originalImageLocalPath?.let { internalRepository.deleteFile(it) }
                }
            }
        }

        return saveResult
    }
}
