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
        price: Long?,
        currency: Currency,
        createdAt: Instant,
        dateOfPurchase: LocalDate?,
        description: String,
        imageUriString: String?,
        originalImageLocalPath: String?,
    ): Result<Unit, List<Error>> {
        val errors: MutableList<Error> =
            itemValidator.validateAll(name, dateOfPurchase).toMutableList()

        if (errors.isNotEmpty()) {
            return Result.Error(errors)
        }

        val finalImagePath = imageUriString?.let {
            when (val imageResult = internalRepository.saveImage(imageUriString)) {
                is Result.Error -> return Result.Error(listOf(imageResult.error))
                is Result.Success -> imageResult.item
            }
        }

        val item = Item(
            id = itemId ?: Item.DEFAULT_ID, name = name, price = price,
            currency = currency, createdAt = createdAt, dateOfPurchase = dateOfPurchase,
            description = description, imageLocalPath = finalImagePath
        )

        try {
            if (itemsRepository.saveItem(item)) {
                if (originalImageLocalPath != finalImagePath) {
                    originalImageLocalPath?.let { internalRepository.deleteFile(it) }
                }
                return Result.Success(Unit)
            } else {
                if (originalImageLocalPath != finalImagePath) {
                    finalImagePath?.let { internalRepository.deleteFile(it) }
                }
                return Result.Error(listOf(BusinessError.ItemFailedToSave))
            }
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            if (originalImageLocalPath != finalImagePath) {
                finalImagePath?.let { internalRepository.deleteFile(it) }
            }
            return Result.Error(listOf(UnexpectedError(e)))
        }
    }
}
