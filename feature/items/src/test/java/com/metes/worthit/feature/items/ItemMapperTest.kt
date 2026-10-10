package com.metes.worthit.feature.items

import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.testing.testItem
import com.metes.worthit.feature.items.mapper.toUiModel
import com.metes.worthit.feature.items.mapper.toUiModels
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class ItemMapperTest {

    private val itemDomain = testItem

    private val itemUiModel = itemDomain.toUiModel()

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
                    imageLocalPath = "path",
                ),
            )
            add(
                itemDomain.copy(
                    id = 2,
                    name = "name2",
                    dateOfPurchase = LocalDate.of(2026, 10, 11),
                    imageLocalPath = "path2",
                ),
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
