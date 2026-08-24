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
    ): Result<Unit, List<Error>> = try {
        val validatorResult = itemValidator.validateAll(name, description, dateOfPurchase, price)

        if (validatorResult is Result.Error) {
            return Result.Error(validatorResult.data)
        }

        val validatedFields = (validatorResult as Result.Success).data

        var finalImagePath: String? = null

        imageUriString?.let {
            internalRepository.saveImage(imageUriString).onError { error ->
                return Result.Error(listOf(error))
            }.onSuccess { path ->
                finalImagePath = path
            }
        }

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

        val saveResult = if (itemsRepository.saveItem(item)) {
            Result.Success(Unit)
        } else {
            Result.Error(listOf(BusinessError.ItemFailedToSave))
        }.also { saveResult ->
            val isImageChanged = finalImagePath != originalImageLocalPath

            if (isImageChanged) {
                saveResult.onError {
                    finalImagePath?.let { internalRepository.deleteFile(it) }
                }.onSuccess {
                    originalImageLocalPath?.let { internalRepository.deleteFile(it) }
                }
            }
        }

        saveResult
    } catch (c: CancellationException) {
        throw c
    } catch (_: Exception) {
        Result.Error(listOf(BusinessError.ItemFailedToSave))
    }
}
