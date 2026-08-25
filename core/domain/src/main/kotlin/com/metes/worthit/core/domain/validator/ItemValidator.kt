package com.metes.worthit.core.domain.validator

import com.metes.worthit.core.domain.error.BusinessError
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject
import com.metes.worthit.core.domain.utils.Result
import java.math.BigDecimal

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


    fun validatePrice(priceInput: String): Result<BigDecimal?, List<BusinessError>> {
        val trimmedPriceInput = priceInput.trim()
        if (trimmedPriceInput.isBlank()) {
            return Result.Success(null)
        }

        val errors = mutableListOf<BusinessError>()

        val priceBigDecimal = normalizePriceInput(trimmedPriceInput).toBigDecimalOrNull()

        if (priceBigDecimal == null) {
            errors.add(BusinessError.ItemPriceCanContainOnlyNumbers)
        } else {
            if (priceBigDecimal < BigDecimal.ZERO) {
                errors.add(BusinessError.ItemPriceCantBeNegative)
            }
        }

        if (errors.isNotEmpty()) {
            return Result.Error(errors)
        }

        return Result.Success(priceBigDecimal)
    }

    fun validateAll(
        name: String?,
        description: String?,
        dateOfPurchase: LocalDate,
        priceInput: String
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

    fun normalizePriceInput(price: String): String {
        val normalized = price.replace(',', '.')

        return normalized
    }
}

data class ValidatedFields(
    val name: String,
    val description: String?,
    val dateOfPurchase: LocalDate,
    val price: BigDecimal?
)
