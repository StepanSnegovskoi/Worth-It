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

class DeleteItemUseCaseTest {

    private lateinit var itemsRepository: ItemsRepository
    private lateinit var storageRepository: StorageRepository
    private lateinit var deleteItemUseCase: DeleteItemUseCase
    private val testItem = Item(
        id = 1,
        name = "name",
        price = BigDecimal.valueOf(1999L),
        currency = Currency.EUR,
        createdAt = Instant.now(),
        dateOfPurchase = LocalDate.of(2026, 1, 1),
        description = "description",
        imageLocalPath = "testPath",
    )

    @Before
    fun setup() {
        itemsRepository = mockk()
        storageRepository = mockk()
        deleteItemUseCase = DeleteItemUseCase(itemsRepository, storageRepository)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `should return Success when item was deleted successfully`() = runTest {
        coEvery { itemsRepository.deleteItem(testItem.id) } returns true
        coEvery { storageRepository.deleteFile(any()) } returns Result.Success(Unit)

        val result = deleteItemUseCase(testItem.id, testItem.imageLocalPath)
        assertTrue(result is Result.Success)
    }

    @Test
    fun `should return BusinessError ItemFailedToDelete when deleting was failed because of itemsRepository deleting was failed`() =
        runTest {
            coEvery { itemsRepository.deleteItem(testItem.id) } returns false
            val result = deleteItemUseCase(testItem.id, testItem.imageLocalPath)

            assertTrue(result is Result.Error)
            assertEquals(BusinessError.ItemFailedToDelete, result.data)
        }

    @Test
    fun `should return BusinessError ItemFailedToDelete when deleting was failed because of error was thrown`() =
        runTest {
            coEvery { itemsRepository.deleteItem(testItem.id) } answers {
                throw Exception("Test error")
            }
            val result = deleteItemUseCase(testItem.id, testItem.imageLocalPath)

            assertTrue(result is Result.Error)
            assertEquals(BusinessError.ItemFailedToDelete, result.data)
        }

    @Test
    fun `should delete item's image`() = runTest {
        coEvery { itemsRepository.deleteItem(testItem.id) } returns true
        coEvery { storageRepository.deleteFile(any()) } returns Result.Success(Unit)
        val result = deleteItemUseCase(testItem.id, testItem.imageLocalPath)

        assertTrue(result is Result.Success)
        coVerify(exactly = 1) {
            storageRepository.deleteFile(any())
        }
    }
}
