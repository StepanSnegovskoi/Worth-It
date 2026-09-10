package com.metes.worthit.core.database.entity

import com.metes.worthit.core.common.toLocalDateFromUtc
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Item
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant

class ItemDbModelTest {
    private val createdAt = Instant.parse("2024-01-01T12:00:00Z")
    private val dateOfPurchase = Instant.parse("2023-12-28T12:00:00Z")

    private val itemDbModel = ItemDbModel(
        id = 1,
        name = "Bike",
        price = BigDecimal.valueOf(1999),
        currencyName = Currency.EUR.name,
        createdAt = createdAt,
        dateOfPurchase = dateOfPurchase.toEpochMilli().toLocalDateFromUtc(),
        description = "This is my first bike",
        imageLocalPath = "images/bike",
    )

    private val item = Item(
        id = 1,
        name = "Bike",
        price = BigDecimal.valueOf(1999),
        currency = Currency.EUR,
        createdAt = createdAt,
        dateOfPurchase = dateOfPurchase.toEpochMilli().toLocalDateFromUtc(),
        description = "This is my first bike",
        imageLocalPath = "images/bike",
    )

    private val itemDbModelWithNulls = ItemDbModel(
        id = 2,
        name = "Helmet",
        price = null,
        currencyName = Currency.USD.name,
        createdAt = createdAt,
        dateOfPurchase = dateOfPurchase.toEpochMilli().toLocalDateFromUtc(),
        description = null,
        imageLocalPath = null,
    )

    private val itemWithNulls = Item(
        id = 2,
        name = "Helmet",
        price = null,
        currency = Currency.USD,
        createdAt = createdAt,
        dateOfPurchase = dateOfPurchase.toEpochMilli().toLocalDateFromUtc(),
        description = null,
        imageLocalPath = null,
    )

    private val itemDbModelWithNullableCurrencyName = ItemDbModel(
        id = 2,
        name = "Helmet",
        price = null,
        currencyName = null,
        createdAt = createdAt,
        dateOfPurchase = dateOfPurchase.toEpochMilli().toLocalDateFromUtc(),
        description = null,
        imageLocalPath = null,
    )

    private val itemWithDefaultCurrency = Item(
        id = 2,
        name = "Helmet",
        price = null,
        // Currency.EUR - default value
        currency = Currency.EUR,
        createdAt = createdAt,
        dateOfPurchase = dateOfPurchase.toEpochMilli().toLocalDateFromUtc(),
        description = null,
        imageLocalPath = null,
    )

    @Test
    fun `ItemDbModel toEntity returns the same item as Item`() {
        Assert.assertEquals(item, itemDbModel.toEntity())
    }

    @Test
    fun `Item toDbModel returns the same item as ItemDbModel`() {
        Assert.assertEquals(itemDbModel, item.toDbModel())
    }

    @Test
    fun `ItemDbModel toEntity correctly maps when optional fields are null`() {
        Assert.assertEquals(itemWithNulls, itemDbModelWithNulls.toEntity())
    }

    @Test
    fun `Item toDbModel correctly maps when optional fields are null`() {
        Assert.assertEquals(itemDbModelWithNulls, itemWithNulls.toDbModel())
    }

    @Test
    fun `ItemDbModel toEntity correctly maps Currency to default value`() {
        Assert.assertEquals(itemWithDefaultCurrency, itemDbModelWithNullableCurrencyName.toEntity())
    }
}
