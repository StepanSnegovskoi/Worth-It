package com.metes.worthit.core.domain.usecase

import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.repository.ItemsRepository
import com.metes.worthit.core.domain.repository.StorageRepository
import com.metes.worthit.core.domain.utils.Result
import com.metes.worthit.core.domain.validator.ItemValidator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SaveItemUseCaseTest {

    private lateinit var itemsRepository: ItemsRepository
    private lateinit var storageRepository: StorageRepository
    private lateinit var saveItemUseCase: SaveItemUseCase
    private val testItem = Item(
        id = 1,
        name = "name",
        price = BigDecimal.valueOf(1999L),
        currency = Currency.EUR,
        createdAt = Instant.now(),
        dateOfPurchase = LocalDate.of(2026, 1, 1),
        description = "description",
        imageLocalPath = null,
    )

    @Before
    fun setup() {
        val itemValidator = ItemValidator(Clock.systemUTC())
        itemsRepository = mockk(relaxed = true)
        storageRepository = mockk(relaxed = true)
        saveItemUseCase = SaveItemUseCase(itemsRepository, storageRepository, itemValidator)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `should save item successfully when fields are correct`() = runTest {
        coEvery { itemsRepository.saveItem(any()) } returns true

        assertTrue(saveTestItem() is Result.Success)
    }

    @Test
    fun `should returns error when name is incorrect`() = runTest {
        val result = saveTestItem(name = "")

        assertTrue(result is Result.Error)
        assertTrue(result.data.isNotEmpty())
    }

    @Test
    fun `should returns error when price is incorrect`() = runTest {
        val result = saveTestItem(price = "ab")

        assertTrue(result is Result.Error)
        assertTrue(result.data.isNotEmpty())
    }

    @Test
    fun `should returns error when dateOfPurchase in the future`() = runTest {
        val result = saveTestItem(dateOfPurchase = LocalDate.now().plus(5, ChronoUnit.DAYS))

        assertTrue(result is Result.Error)
        assertTrue(result.data.isNotEmpty())
    }

    @Test
    fun `should call internalRepository saveImage when uri is not null`() = runTest {
        coEvery { storageRepository.saveImage("uri", any()) } returns Result.Success("uri")
        coEvery { itemsRepository.saveItem(any()) } returns true

        saveTestItem(imageUriString = "uri")

        coVerify(exactly = 1) {
            storageRepository.saveImage(any(), any())
        }
    }

    @Test
    fun `should delete previous image if it was changed`() = runTest {
        coEvery { storageRepository.saveImage("newUri", any()) } returns Result.Success("uri")
        coEvery { itemsRepository.saveItem(any()) } returns true

        saveTestItem(imageUriString = "newUri", originalImageLocalPath = "oldPath")

        coVerify(exactly = 1) {
            storageRepository.deleteFile("oldPath")
        }
    }

    @Test
    fun `should delete final image if error was thrown`() = runTest {
        coEvery { storageRepository.saveImage(any(), any()) } returns Result.Success("newImagePath")
        coEvery { itemsRepository.saveItem(any()) } answers {
            throw Exception("Test error")
        }

        saveTestItem(imageUriString = "newUri", originalImageLocalPath = "oldPath")

        coVerify(exactly = 1) {
            storageRepository.deleteFile("newImagePath")
        }
    }

    @Test
    fun `should return error with BusinessError ItemFailedToSave if item was not saved successfully`() =
        runTest {
            coEvery { storageRepository.saveImage("newUri", any()) } returns Result.Success("uri")
            coEvery { itemsRepository.saveItem(any()) } answers {
                throw Exception("Test error")
            }

            val result = saveTestItem()

            assertTrue(result is Result.Error)
            assertEquals(1, result.data.size)
            assertEquals(BusinessError.ItemFailedToSave, result.data.first())
        }

    private suspend fun saveTestItem(
        itemId: Int? = testItem.id,
        name: String = testItem.name,
        price: String = testItem.price.toString(),
        currency: Currency = testItem.currency,
        createdAt: Instant = testItem.createdAt,
        dateOfPurchase: LocalDate = testItem.dateOfPurchase,
        description: String = testItem.description ?: "",
        imageUriString: String? = null,
        originalImageLocalPath: String? = null,
    ) = saveItemUseCase(
        itemId = itemId,
        name = name,
        price = price,
        currency = currency,
        createdAt = createdAt,
        dateOfPurchase = dateOfPurchase,
        description = description,
        imageUriString = imageUriString,
        originalImageLocalPath = originalImageLocalPath,
    )
}
