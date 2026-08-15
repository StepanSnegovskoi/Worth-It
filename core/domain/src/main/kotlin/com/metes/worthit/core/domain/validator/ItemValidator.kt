package com.metes.worthit.core.domain.validator

import com.metes.worthit.core.domain.error.BusinessError
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject
import com.metes.worthit.core.domain.utils.Result

class ItemValidator @Inject constructor(
    private val clock: Clock,
) {
    fun validateName(name: String?): Result<String, List<BusinessError>> {
        val trimmedName = name?.trim()
        return if (trimmedName.isNullOrBlank()) {
            Result.Error(listOf(BusinessError.ItemNameIsBlank))
        } else {
            Result.Success(trimmedName)
        }
    }

    fun validateDescription(description: String?): Result<String?, List<BusinessError>> {
        return Result.Success(description?.trim())
    }

    fun validatePurchaseDate(date: LocalDate): Result<LocalDate, List<BusinessError>> {
        return if (date.isAfter(LocalDate.now(clock))) {
            Result.Error(listOf(BusinessError.ItemPurchaseDateInTheFuture))
        } else {
            Result.Success(date)
        }
    }


    fun validatePrice(priceInput: String?): Result<Long?, List<BusinessError>> {
        val trimmedPriceInput = priceInput?.trim()
        if (trimmedPriceInput.isNullOrBlank()) {
            return Result.Success(null)
        }

        val errors = mutableListOf<BusinessError>()

        if (trimmedPriceInput.length > MAX_PRICE_LENGTH) {
            errors.add(BusinessError.ItemPriceLengthCantBeMoreThan(MAX_PRICE_LENGTH))
        }

        if (!trimmedPriceInput.all { it.isDigit() }) {
            errors.add(BusinessError.ItemPriceInvalidFormat)
        }

        if (errors.isNotEmpty()) {
            return Result.Error(errors)
        }

        val price = trimmedPriceInput
            .toLongOrNull() ?: return Result.Error(listOf(BusinessError.ItemPriceInvalidFormat))

        return Result.Success(price)
    }

    fun validateAll(
        name: String?,
        description: String?,
        dateOfPurchase: LocalDate,
        priceInput: String?
    ): Result<ValidatedFields, List<BusinessError>> {
        val nameResult = validateName(name)
        val descriptionResult = validateDescription(description)
        val dateResult = validatePurchaseDate(dateOfPurchase)
        val priceResult = validatePrice(priceInput)

        val errors = buildList {
            if (nameResult is Result.Error) addAll(nameResult.data)
            if (descriptionResult is Result.Error) addAll(descriptionResult.data)
            if (dateResult is Result.Error) addAll(dateResult.data)
            if (priceResult is Result.Error) addAll(priceResult.data)
        }

        if (errors.isNotEmpty()) {
            return Result.Error(errors)
        }

        return Result.Success(
            ValidatedFields(
                name = (nameResult as Result.Success).data,
                description = (descriptionResult as Result.Success).data,
                dateOfPurchase = (dateResult as Result.Success).data,
                price = (priceResult as Result.Success).data
            )
        )
    }

    companion object {
        private const val MAX_PRICE_LENGTH = Long.MAX_VALUE.toString().length - 1
    }
}

data class ValidatedFields(
    val name: String,
    val description: String?,
    val dateOfPurchase: LocalDate,
    val price: Long?
)
