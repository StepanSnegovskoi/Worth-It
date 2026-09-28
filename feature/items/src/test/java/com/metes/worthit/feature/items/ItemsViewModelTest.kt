package com.metes.worthit.feature.items

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.metes.worthit.core.domain.usecase.DeleteItemUseCase
import com.metes.worthit.core.domain.usecase.DeleteItemsUseCase
import com.metes.worthit.core.domain.usecase.ObserveItemsUseCase
import com.metes.worthit.core.testing.MainDispatcherRule
import com.metes.worthit.core.testing.testItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds

class ItemsViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private lateinit var deleteItemUseCase: DeleteItemUseCase
    private lateinit var deleteItemsUseCase: DeleteItemsUseCase
    private lateinit var observeItemsUseCase: ObserveItemsUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    private val sampleItems = listOf(
        testItem.copy(id = 1, name = "Apple iPhone", imageLocalPath = "/path/1"),
        testItem.copy(id = 2, name = "Samsung Galaxy", imageLocalPath = "/path/2"),
        testItem.copy(id = 3, name = "Apple iPad", imageLocalPath = null),
    )

    @Before
    fun setup() {
        deleteItemUseCase = mockk(relaxed = true)
        deleteItemsUseCase = mockk(relaxed = true)
        observeItemsUseCase = mockk()
        savedStateHandle = SavedStateHandle()

        every { observeItemsUseCase() } returns flowOf(sampleItems)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    private fun createViewModel(
        handle: SavedStateHandle = savedStateHandle,
    ): ItemsViewModel {
        return ItemsViewModel(
            deleteItemUseCase = deleteItemUseCase,
            deleteItemsUseCase = deleteItemsUseCase,
            savedStateHandle = handle,
            defaultDispatcher = rule.testDispatcher,
            observeItemsUseCase = observeItemsUseCase,
        )
    }

    @Test
    fun `initial state is Loading before upstream emits`() = runTest {
        every { observeItemsUseCase() } returns flow {
            delay(100.milliseconds)
            emit(sampleItems)
        }
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            assertIs<ItemsUiState.Loading>(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state updates to Success after upstream emits`() = runTest {
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            val state = awaitItem()
            assertIs<ItemsUiState.Success>(state)
            assertEquals(3, state.items.size)
            assertEquals(3, state.filteredItemsByQuery.size)
            assertTrue(state.selectedItemIds.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `ChangeSearchQuery filters items starting with query case-insensitively`() = runTest {
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            awaitItem()

            viewModel.processCommand(ItemsCommand.ChangeSearchQuery("apple"))

            val filteredState = awaitItem() as ItemsUiState.Success
            assertEquals(2, filteredState.filteredItemsByQuery.size)
            assertTrue(filteredState.filteredItemsByQuery.all { it.name.startsWith("Apple") })
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `CleanSearchQueryClick resets query and shows all items`() = runTest {
        savedStateHandle["search_query"] = "apple"
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            val filteredState = awaitItem() as ItemsUiState.Success
            assertEquals(2, filteredState.filteredItemsByQuery.size)

            viewModel.processCommand(ItemsCommand.CleanSearchQueryClick)

            val resetState = awaitItem() as ItemsUiState.Success
            assertEquals(3, resetState.filteredItemsByQuery.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `LongClickItem toggles selection mode when no items selected`() = runTest {
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            awaitItem()
            viewModel.processCommand(ItemsCommand.LongClickItem(itemId = 1))

            val selectedState = awaitItem() as ItemsUiState.Success
            assertEquals(setOf(1), selectedState.selectedItemIds)

            viewModel.events.test {
                viewModel.processCommand(ItemsCommand.LongClickItem(itemId = 2))
                val event = awaitItem()
                assertIs<ItemsEvent.NavigateToSaveItem>(event)
                assertEquals(2, event.itemId)
                cancelAndIgnoreRemainingEvents()
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `ClickItem toggles selection when selection mode is already active`() = runTest {
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            awaitItem()

            viewModel.processCommand(ItemsCommand.LongClickItem(itemId = 1))
            awaitItem()

            viewModel.processCommand(ItemsCommand.ClickItem(itemId = 2))
            val stateWithTwo = awaitItem() as ItemsUiState.Success
            assertEquals(setOf(1, 2), stateWithTwo.selectedItemIds)

            viewModel.processCommand(ItemsCommand.ClickItem(itemId = 1))
            val stateWithOne = awaitItem() as ItemsUiState.Success
            assertEquals(setOf(2), stateWithOne.selectedItemIds)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `UnselectItems clears all selected item IDs`() = runTest {
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            awaitItem()

            viewModel.processCommand(ItemsCommand.LongClickItem(itemId = 1))
            awaitItem()

            viewModel.processCommand(ItemsCommand.UnselectItems)
            val clearedState = awaitItem() as ItemsUiState.Success
            assertTrue(clearedState.selectedItemIds.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `selected items are pruned if they disappear from filtered search result`() = runTest {
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            awaitItem()

            viewModel.processCommand(ItemsCommand.LongClickItem(itemId = 2))
            val stateWithSelection = awaitItem() as ItemsUiState.Success
            assertEquals(setOf(2), stateWithSelection.selectedItemIds)

            viewModel.processCommand(ItemsCommand.ChangeSearchQuery("apple"))
            val filteredState = awaitItem() as ItemsUiState.Success
            assertTrue(filteredState.selectedItemIds.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `ClickItem sends NavigateToSaveItem event when nothing is selected`() = runTest {
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            awaitItem()

            viewModel.events.test {
                viewModel.processCommand(ItemsCommand.ClickItem(itemId = 42))

                val event = awaitItem()
                assertIs<ItemsEvent.NavigateToSaveItem>(event)
                assertEquals(42, event.itemId)
                cancelAndIgnoreRemainingEvents()
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `DeleteItem executes useCase and removes item from selected ids`() = runTest {
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            awaitItem()

            viewModel.processCommand(ItemsCommand.LongClickItem(itemId = 1))
            awaitItem()

            viewModel.processCommand(
                ItemsCommand.DeleteItem(itemId = 1, itemLocalImagePath = "/path/1")
            )

            coVerify(exactly = 1) { deleteItemUseCase(1, "/path/1") }

            val updatedState = awaitItem() as ItemsUiState.Success
            assertTrue(updatedState.selectedItemIds.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `DeleteItems resolves local paths and calls deleteItemsUseCase`() = runTest {
        val viewModel = createViewModel()

        viewModel.itemsUiState.test {
            awaitItem()

            viewModel.processCommand(
                ItemsCommand.DeleteItems(itemIds = setOf(1, 2))
            )

            coVerify(exactly = 1) {
                deleteItemsUseCase(
                    itemIds = match { it.containsAll(listOf(1, 2)) },
                    itemLocalImagePaths = setOf("/path/1", "/path/2")
                )
            }

            cancelAndIgnoreRemainingEvents()
        }
    }
}
