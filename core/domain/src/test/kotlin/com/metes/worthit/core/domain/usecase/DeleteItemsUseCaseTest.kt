package com.metes.worthit.core.domain.usecase

import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.repository.ItemsRepository
import com.metes.worthit.core.domain.repository.StorageRepository
import com.metes.worthit.core.domain.utils.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeleteItemsUseCaseTest {

    private lateinit var itemsRepository: ItemsRepository
    private lateinit var storageRepository: StorageRepository
    private lateinit var deleteItemsUseCase: DeleteItemsUseCase
    private val testItems = buildList {
        repeat(3) {
            val item = Item(
                id = 1,
                name = "name",
                price = BigDecimal.valueOf(1999L),
                currency = Currency.EUR,
                createdAt = Instant.now(),
                dateOfPurchase = LocalDate.of(2026, 1, 1),
                description = "description",
                imageLocalPath = "testPath",
            )
            add(item)
        }
    }
    private val testItemsIds = testItems.map { it.id }
    private val testItemsPaths = testItems.map { it.imageLocalPath }.toSet()

    @Before
    fun setup() {
        itemsRepository = mockk()
        storageRepository = mockk()
        deleteItemsUseCase = DeleteItemsUseCase(itemsRepository, storageRepository)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `should return Success when items were deleted successfully`() = runTest {
        coEvery { itemsRepository.deleteItems(any()) } returns Unit
        coEvery { storageRepository.deleteFile(any()) } returns Result.Success(Unit)

        val result = deleteItemsUseCase(testItemsIds, testItemsPaths)
        assertTrue(result is Result.Success)
    }

    @Test
    fun `should return BusinessError ItemFailedToDelete when deleting was failed because of itemsRepository deleting was failed`() =
        runTest {
            coEvery { itemsRepository.deleteItems(any()) } returns Unit
            val result = deleteItemsUseCase(testItemsIds, testItemsPaths)

            assertTrue(result is Result.Error)
            assertEquals(BusinessError.ItemFailedToDelete, result.data)
        }

    @Test
    fun `should return BusinessError ItemFailedToDelete when deleting was failed because of error was thrown`() =
        runTest {
            coEvery { itemsRepository.deleteItems(any()) } answers {
                throw Exception("Test error")
            }
            val result = deleteItemsUseCase(testItemsIds, testItemsPaths)

            assertTrue(result is Result.Error)
            assertEquals(BusinessError.ItemFailedToDelete, result.data)
        }

    @Test
    fun `should delete images of all items`() = runTest {
        coEvery { itemsRepository.deleteItems(any()) } returns Unit
        coEvery { storageRepository.deleteFile(any()) } returns Result.Success(Unit)
        val result = deleteItemsUseCase(testItemsIds, testItemsPaths)

        assertTrue(result is Result.Success)
        coVerify(exactly = testItemsPaths.size) {
            storageRepository.deleteFile(any())
        }
    }
}
