package com.metes.worthit.core.domain.usecase

import app.cash.turbine.test
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.repository.ItemsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.unmockkConstructor
import io.mockk.unmockkObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ObserveItemsUseCaseTest {

    private lateinit var repository: ItemsRepository
    private lateinit var observeItemsUseCase: ObserveItemsUseCase

    @Before
    fun setup() {
        repository = mockk()
        observeItemsUseCase = ObserveItemsUseCase(repository)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `should return correct list of item`() = runTest {
        val itemsList = listOf(Item(
            id = 1,
            name = "name",
            price = null,
            currency = Currency.EUR,
            createdAt = Instant.now(),
            dateOfPurchase = LocalDate.of(2026, 1, 1),
            description = "description",
            imageLocalPath = null,
        ))

        every { repository.observeItems() } returns flow {
            emit(emptyList())
            emit(itemsList)
        }

        observeItemsUseCase().test {
            assertEquals(emptyList(), awaitItem())
            assertEquals(itemsList, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
