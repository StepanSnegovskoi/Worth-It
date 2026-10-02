package com.metes.worthit.core.database.entity

import com.metes.worthit.core.common.toLocalDateFromUtc
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.testing.testItem
import org.junit.Assert
import org.junit.Test
import java.time.Instant

class ItemDbModelTest {

    private val itemDbModel = testItem.toDbModel()

    private val itemDbModelWithNulls = testItem(
        price = null,
        description = null,
        imageLocalPath = null,
    ).toDbModel()

    private val itemWithNulls = testItem(
        price = null,
        description = null,
        imageLocalPath = null,
    )

    private val itemDbModelWithNullableCurrencyName = ItemDbModel(
        id = 2,
        name = "Helmet",
        price = null,
        currencyName = null,
        createdAt = Instant.parse("2024-01-01T12:00:00Z"),
        dateOfPurchase = Instant.parse("2023-12-28T12:00:00Z").toEpochMilli().toLocalDateFromUtc(),
        description = null,
        imageLocalPath = null,
    )

    private val itemWithDefaultCurrency = itemDbModelWithNullableCurrencyName
        .copy(currencyName = Currency.EUR.name)
        .toEntity()

    @Test
    fun `ItemDbModel toEntity returns the same item as Item`() {
        Assert.assertEquals(testItem, itemDbModel.toEntity())
    }

    @Test
    fun `Item toDbModel returns the same item as ItemDbModel`() {
        Assert.assertEquals(itemDbModel, testItem.toDbModel())
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
