package com.metes.worthit.core.domain.validator

import com.metes.worthit.core.domain.error.BusinessError
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject
import com.metes.worthit.core.domain.utils.Result

class ItemValidator @Inject constructor(
    private val clock: Clock,
) {
    fun validateName(name: String?): Result<Unit, BusinessError> {
        return if (name.isNullOrBlank()) {
            Result.Error(BusinessError.ItemNameIsBlank)
        } else {
            Result.Success(Unit)
        }
    }

    fun validatePurchaseDate(date: LocalDate?): Result<Unit, BusinessError> {
        if (date == null) return Result.Success(Unit)

        return if (date.isAfter(LocalDate.now(clock))) {
            Result.Error(BusinessError.ItemPurchaseDateInTheFuture)
        } else {
            Result.Success(Unit)
        }
    }

    fun validateAll(name: String?, dateOfPurchase: LocalDate?): List<BusinessError> {
        val result = buildList {
            add(validateName(name))
            add(validatePurchaseDate(dateOfPurchase))
        }

        val errors = result
            .filterIsInstance<Result.Error<BusinessError>>()
            .map { it.error }

        return errors
    }
}
