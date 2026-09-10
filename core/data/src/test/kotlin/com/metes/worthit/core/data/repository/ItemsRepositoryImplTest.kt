package com.metes.worthit.core.data.repository

import app.cash.turbine.test
import com.metes.worthit.core.database.db.ItemsDao
import com.metes.worthit.core.database.entity.ItemDbModel
import com.metes.worthit.core.database.entity.toEntities
import com.metes.worthit.core.database.entity.toEntity
import com.metes.worthit.core.domain.entity.Currency
import io.mockk.mockk

import com.metes.worthit.core.domain.repository.ItemsRepository
import io.mockk.awaits
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.verify
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.bouncycastle.crypto.generators.SCrypt
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ItemsRepositoryImplTest {

    private lateinit var itemsRepository: ItemsRepository
    private val dao: ItemsDao = mockk<ItemsDao>()

    private val scheduler = TestCoroutineScheduler()
    private val dispatcher = StandardTestDispatcher(scheduler)

    private val testItemDb = ItemDbModel(
        id = 1,
        name = "Bike",
        price = BigDecimal.valueOf(1999L),
        currencyName = Currency.EUR.name,
        createdAt = Instant.now(),
        dateOfPurchase = LocalDate.now(),
        description = "This is my first bike",
        imageLocalPath = null
    )

    @Before
    fun setup() {
        itemsRepository = ItemsRepositoryImpl(
            dao = dao,
            ioDispatcher = dispatcher,
        )
    }

    @After
    fun teardown() {
        clearMocks(dao)
    }

    @Test
    fun `getItemById returns domain entity when item exists`() = runTest(dispatcher) {
        val expectedEntity = testItemDb.toEntity()
        coEvery { dao.getItemById(testItemDb.id) } returns testItemDb

        val actualItem = itemsRepository.getItemById(testItemDb.id)

        coVerify(exactly = 1) { dao.getItemById(testItemDb.id) }
        assertEquals(expectedEntity, actualItem)
    }

    @Test
    fun `getItemById returns null when item does not exist`() = runTest(dispatcher) {
        val nonExistentId = 111
        coEvery { dao.getItemById(nonExistentId) } returns null

        val actualItem = itemsRepository.getItemById(nonExistentId)

        coVerify(exactly = 1) { dao.getItemById(111) }
        assertEquals(null, actualItem)
    }

    @Test
    fun `saveItem returns true when dao successfully inserts or updates item`() =
        runTest(dispatcher) {
            coEvery { dao.saveItem(testItemDb) } returns 1L

            val isSaved = itemsRepository.saveItem(testItemDb.toEntity())

            coVerify(exactly = 1) { dao.saveItem(testItemDb) }
            assertTrue(isSaved)
        }

    @Test
    fun `deleteItems invokes dao deleteItems once`() = runTest(dispatcher) {
            val targetIds = listOf(1, 2, 3)
            coEvery { dao.deleteItems(targetIds) } returns targetIds.size

            itemsRepository.deleteItems(targetIds)

            coVerify(exactly = 1) { dao.deleteItems(targetIds) }
        }

    @Test
    fun `deleteItem returns true when row count is greater than zero`() = runTest(dispatcher) {
        coEvery { dao.deleteItem(testItemDb.id) } returns 1

        val isDeleted = itemsRepository.deleteItem(testItemDb.id)

        coVerify(exactly = 1) { dao.deleteItem(testItemDb.id) }
        assertTrue(isDeleted)
    }

    @Test
    fun `deleteItem returns false when item was not found in database`() = runTest(dispatcher) {
        val nonExistentId = 404
        coEvery { dao.deleteItem(nonExistentId) } returns 0

        val isDeleted = itemsRepository.deleteItem(nonExistentId)

        coVerify(exactly = 1) { dao.deleteItem(nonExistentId) }
        assertFalse(isDeleted)
    }

    @Test
    fun `observeItems emits correctly mapped domain items stream`() = runTest(dispatcher) {
        val initialBatch = listOf(testItemDb, testItemDb.copy(id = 2))
        val updatedBatch = initialBatch + testItemDb.copy(id = 3)

        val itemsFlow = flow {
            emit(initialBatch)
            emit(updatedBatch)
        }
        coEvery { dao.observeItems() } returns itemsFlow

        itemsRepository.observeItems().test {
            assertEquals(initialBatch.toEntities(), awaitItem())
            assertEquals(updatedBatch.toEntities(), awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) { dao.observeItems() }
    }
}
