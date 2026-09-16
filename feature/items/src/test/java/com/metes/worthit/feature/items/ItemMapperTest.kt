package com.metes.worthit.feature.items

import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.feature.items.mapper.toUiModel
import com.metes.worthit.feature.items.mapper.toUiModels
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate

class ItemMapperTest {

    private val itemDomain = Item(
        id = 1,
        name = "Bike",
        price = BigDecimal.valueOf(1999L),
        currency = Currency.CNY,
        createdAt = Instant.parse("2026-09-05T10:00:00Z"),
        dateOfPurchase = LocalDate.of(2026, 9, 5),
        description = "This is my first bike",
        imageLocalPath = null,
    )

    private val itemUiModel = ItemUiModel(
        id = 1,
        name = "Bike",
        dateOfPurchase = LocalDate.of(2026, 9, 5),
        localImagePath = null,
    )

    @Test
    fun `toUiModel maps item correctly`() {
        assertEquals(itemUiModel, itemDomain.toUiModel())
    }

    @Test
    fun `toUiModels returns empty list when list of items was empty`() {
        val items = listOf<Item>()
        assertEquals(emptyList<ItemUiModel>(), items.toUiModels())
    }

    @Test
    fun `toUiModels maps items correctly`() {
        val items = buildList {
            add(
                itemDomain.copy(
                    id = 1,
                    name = "name",
                    dateOfPurchase = LocalDate.of(2026, 10, 10),
                    imageLocalPath = "path"
                )
            )
            add(
                itemDomain.copy(
                    id = 2,
                    name = "name2",
                    dateOfPurchase = LocalDate.of(2026, 10, 11),
                    imageLocalPath = "path2"
                )
            )
        }

        val mapped = items.toUiModels()

        assertEquals(1, mapped[0].id)
        assertEquals("name", mapped[0].name)
        assertEquals(LocalDate.of(2026, 10, 10), mapped[0].dateOfPurchase)
        assertEquals("path", mapped[0].localImagePath)

        assertEquals(2, mapped[1].id)
        assertEquals("name2", mapped[1].name)
        assertEquals(LocalDate.of(2026, 10, 11), mapped[1].dateOfPurchase)
        assertEquals("path2", mapped[1].localImagePath)
    }
}
