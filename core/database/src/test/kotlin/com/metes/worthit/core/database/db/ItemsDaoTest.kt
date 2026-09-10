package com.metes.worthit.core.database.db

import android.content.Context
import androidx.room3.Room
import androidx.room3.withWriteTransaction
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.metes.worthit.core.common.toLocalDateFromUtc
import com.metes.worthit.core.database.entity.ItemDbModel
import com.metes.worthit.core.domain.entity.Currency
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.math.BigDecimal
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class ItemsDaoTest {

    private lateinit var dao: ItemsDao
    private lateinit var db: WorthItDatabase
    private val itemDbModel = ItemDbModel(
        id = 1,
        name = "Bike",
        price = BigDecimal.valueOf(1999),
        currencyName = Currency.EUR.name,
        createdAt = Instant.parse("2024-01-01T12:00:00Z"),
        dateOfPurchase = Instant.parse("2023-12-28T12:00:00Z").toEpochMilli().toLocalDateFromUtc(),
        description = "This is my first bike",
        imageLocalPath = "images/bike",
    )

    private val testItems = buildList {
        repeat(5) {
            val item = itemDbModel.copy(id = it + 1)
            add(item)
        }
    }

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder<WorthItDatabase>(context = context).build()
        dao = db.dao()
    }

    @After
    fun after() {
        db.close()
    }

    @Test
    fun `saveItem saves item and getItemById returns item with requested id correctly`() = runTest {
        dao.saveItem(itemDbModel)
        assertEquals(itemDbModel, dao.getItemById(itemDbModel.id))
    }

    @Test
    fun `observeItems returns items correctly`() = runTest {
        insertItemsTransaction()
        dao.observeItems().test {
            val itemsFromDao = awaitItem()
            assertEquals(testItems, itemsFromDao)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `deleteItem deletes item correctly`() = runTest {
        dao.observeItems().test {
            assertTrue(awaitItem().isEmpty())
            dao.saveItem(itemDbModel)
            assertEquals(itemDbModel, awaitItem().first())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `deleteItems deletes items correctly`() = runTest {
        dao.observeItems().test {
            assertTrue(awaitItem().isEmpty())

            insertItemsTransaction()

            assertEquals(testItems, awaitItem())

            dao.deleteItems(testItems.map { it.id })
            assertTrue(awaitItem().isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    private suspend fun insertItemsTransaction(items: List<ItemDbModel> = testItems): List<ItemDbModel> {
        db.withWriteTransaction {
            items.forEach {
                dao.saveItem(it)
            }
        }
        return items
    }
}
