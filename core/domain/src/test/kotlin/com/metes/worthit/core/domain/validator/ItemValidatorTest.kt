package com.metes.worthit.core.domain.validator

import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.utils.Result
import java.time.Clock
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ItemValidatorTest {

    private val maxCountOfDigitsInIntegerPartOfPrice = 13
    private val maxPrice = buildString {
        repeat(maxCountOfDigitsInIntegerPartOfPrice) {
            append("9")
        }
    }
    private val clock = Clock.systemUTC()
    private val validator = ItemValidator(clock)

    @Test
    fun `validateName returns BusinessError ItemNameIsBlank when name is not correct`() {
        val emptyName = " "
        val resultForEmptyName = validator.validateName(emptyName)

        assertTrue(resultForEmptyName is Result.Error)
        assertEquals(1, resultForEmptyName.data.size)
        assertEquals(BusinessError.ItemNameIsBlank, resultForEmptyName.data.first())

        val nullName: String? = null
        val resultForNullName = validator.validateName(nullName)

        assertTrue(resultForNullName is Result.Error)
        assertEquals(1, resultForNullName.data.size)
        assertEquals(BusinessError.ItemNameIsBlank, resultForNullName.data.first())
    }

    @Test
    fun `validateName returns result with provided name when name is correct`() {
        val correctName = " Bike  "
        val result = validator.validateName(correctName)

        assertTrue(result is Result.Success)
        assertEquals(correctName.trim(), result.data)
    }

    @Test
    fun `validateDescription returns result with trimmed description when description is not null`() {
        val description = "   Description "
        val result = validator.validateDescription(description)

        assertTrue(result is Result.Success)
        assertEquals(description.trim(), result.data)
    }

    @Test
    fun `validatePurchaseDate returns result with provided date when date is before current date`() {
        val date = LocalDate.now().minus(5, ChronoUnit.DAYS)
        val result = validator.validatePurchaseDate(date)

        assertTrue(result is Result.Success)
        assertEquals(date, result.data)
    }

    @Test
    fun `validatePurchaseDate returns result with BusinessError ItemPurchaseDateInTheFuture date when date is after current date`() {
        val date = LocalDate.now().plus(5, ChronoUnit.DAYS)
        val result = validator.validatePurchaseDate(date)

        assertTrue(result is Result.Error)
        assertEquals(1, result.data.size)
        assertEquals(BusinessError.ItemPurchaseDateInTheFuture, result.data.first())
    }

    @Test
    fun `validatePrice returns result with price when price with digits is correct`() {
        val price = "1000"
        val result = validator.validatePrice(price)

        assertTrue(result is Result.Success)
        assertEquals(price.toBigDecimal(), result.data)
    }

    @Test
    fun `validatePrice returns result with null when price is blank`() {
        val price = ""
        val result = validator.validatePrice(price)

        assertTrue(result is Result.Success)
        assertEquals(null, result.data)
    }

    @Test
    fun `validatePrice returns result with BusinessError ItemPriceCanContainOnlyNumbers when price contains chars`() {
        val price = "abc"
        val result = validator.validatePrice(price)

        assertTrue(result is Result.Error)
        assertTrue(result.data.contains(BusinessError.ItemPriceCanContainOnlyNumbers))
    }

    @Test
    fun `validatePrice returns result with BusinessError ItemPriceCantBeNegative when price is negative`() {
        val price = "-1000"
        val result = validator.validatePrice(price)

        assertTrue(result is Result.Error)
        assertTrue(result.data.contains(BusinessError.ItemPriceCantBeNegative))
    }

    @Test
    fun `validatePrice returns result with BusinessError ItemPriceCantBeMoreThan when price is negative`() {
        val price = buildString {
            repeat(maxCountOfDigitsInIntegerPartOfPrice + 5) {
                append("9")
            }
        }
        val result = validator.validatePrice(price)

        assertTrue(result is Result.Error)
        assertEquals(1, result.data.filterIsInstance<BusinessError.ItemPriceCantBeMoreThan>().size)

        assertEquals(
            expected = BusinessError.ItemPriceCantBeMoreThan(
                price = maxPrice,
                maxCountOfDigitsInIntegerPrice = maxCountOfDigitsInIntegerPartOfPrice,
            ),
            actual = result.data.filterIsInstance<BusinessError.ItemPriceCantBeMoreThan>().first()
        )
    }

    @Test
    fun `validateAll returns result with validated fields if all fields are correct`() {
        val name = "   Bike  "
        val description = "  This is my first bike   "
        val price = " 1000   "
        val dateOfPurchase = LocalDate.now().minus(5, ChronoUnit.DAYS)

        val result = validator.validateAll(
            name = name,
            description = description,
            dateOfPurchase = dateOfPurchase,
            priceInput = price,
        )

        assertTrue(result is Result.Success)

        val expectedField = ValidatedFields(
            name = name.trim(),
            description = description.trim(),
            dateOfPurchase = dateOfPurchase,
            price = price.trim().toBigDecimal(),
        )
        assertEquals(expectedField, result.data)
    }

    @Test
    fun `validateAll returns result with errors when price is incorrect`() {
        val name = "   Bike  "
        val description = "  This is my first bike   "
        val price = "a"
        val dateOfPurchase = LocalDate.now().minus(5, ChronoUnit.DAYS)

        val result = validator.validateAll(
            name = name,
            description = description,
            dateOfPurchase = dateOfPurchase,
            priceInput = price,
        )

        assertTrue(result is Result.Error)
        assertTrue(result.data.isNotEmpty())
    }

    @Test
    fun `validateAll returns result with errors when name is incorrect`() {
        val name = ""
        val description = "  This is my first bike   "
        val price = "1111"
        val dateOfPurchase = LocalDate.now().minus(5, ChronoUnit.DAYS)

        val result = validator.validateAll(
            name = name,
            description = description,
            dateOfPurchase = dateOfPurchase,
            priceInput = price,
        )

        assertTrue(result is Result.Error)
        assertTrue(result.data.isNotEmpty())
    }

    @Test
    fun `validateAll returns result with errors when dateOfPurchase is incorrect`() {
        val name = "   Bike  "
        val description = "  This is my first bike   "
        val price = "1234"
        val dateOfPurchase = LocalDate.now().plus(5, ChronoUnit.DAYS)

        val result = validator.validateAll(
            name = name,
            description = description,
            dateOfPurchase = dateOfPurchase,
            priceInput = price,
        )

        assertTrue(result is Result.Error)
        assertTrue(result.data.isNotEmpty())
    }
}
