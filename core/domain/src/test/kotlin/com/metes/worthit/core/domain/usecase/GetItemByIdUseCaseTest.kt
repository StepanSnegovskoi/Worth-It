package com.metes.worthit.core.domain.usecase

import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.UnexpectedError
import com.metes.worthit.core.domain.repository.ItemsRepository
import com.metes.worthit.core.domain.utils.Result
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import java.time.Instant
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetItemByIdUseCaseTest {

    private lateinit var repository: ItemsRepository
    private lateinit var getItemByIdUseCase: GetItemByIdUseCase

    @Before
    fun setup() {
        repository = mockk()
        getItemByIdUseCase = GetItemByIdUseCase(repository)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `should return item with requested id if it exists`() = runTest {
        val item = Item(
            id = 1,
            name = "name",
            price = null,
            currency = Currency.EUR,
            createdAt = Instant.now(),
            dateOfPurchase = LocalDate.of(2026, 1, 1),
            description = "description",
            imageLocalPath = null,
        )
        coEvery { repository.getItemById(item.id) } returns item
        val result = getItemByIdUseCase(item.id)

        assertTrue(result is Result.Success)
        assertEquals(item, result.data)
    }

    @Test
    fun `should return null if item does not exist`() = runTest {
        coEvery { repository.getItemById(1) } returns null
        val result = getItemByIdUseCase(1)

        assertTrue(result is Result.Success)
        assertEquals(null, result.data)
    }

    @Test
    fun `should return BusinessError ItemNotFound if IllegalStateException was thrown`() = runTest {
        coEvery { repository.getItemById(1) } answers {
            throw IllegalStateException("Test error")
        }
        val result = getItemByIdUseCase(1)

        assertTrue(result is Result.Error)
        assertEquals(BusinessError.ItemNotFound, result.data.first())
    }

    @Test
    fun `should return UnexpectedError if Exception was thrown`() = runTest {
        val exception = Exception("Test error")
        coEvery { repository.getItemById(1) } answers {
            throw exception
        }
        val result = getItemByIdUseCase(1)

        assertTrue(result is Result.Error)
        assertEquals(UnexpectedError(exception), result.data.first())
    }
}
