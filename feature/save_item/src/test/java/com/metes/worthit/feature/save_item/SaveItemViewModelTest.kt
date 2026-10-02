package com.metes.worthit.feature.save_item

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.error.UnexpectedError
import com.metes.worthit.core.domain.usecase.DeleteItemUseCase
import com.metes.worthit.core.domain.usecase.GetItemByIdUseCase
import com.metes.worthit.core.domain.usecase.SaveItemUseCase
import com.metes.worthit.core.domain.utils.DateProvider
import com.metes.worthit.core.domain.utils.Result
import com.metes.worthit.core.domain.utils.UserSettings
import com.metes.worthit.core.domain.validator.ItemValidator
import com.metes.worthit.core.testing.FakeCurrentDateProvider
import com.metes.worthit.core.testing.FakeUserSettings
import com.metes.worthit.core.testing.MainDispatcherRule
import com.metes.worthit.core.testing.testClock
import com.metes.worthit.core.testing.testItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertIs

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class SaveItemViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()
    private val saveItemUseCase: SaveItemUseCase = mockk()
    private val getItemByIdUseCase: GetItemByIdUseCase = mockk()
    private val deleteItemUseCase: DeleteItemUseCase = mockk()

    private lateinit var userSettings: UserSettings
    private lateinit var currentDateProvider: DateProvider
    private lateinit var itemValidator: ItemValidator

    @Before
    fun setup() {
        itemValidator = ItemValidator(testClock)
        userSettings = FakeUserSettings()
        currentDateProvider = FakeCurrentDateProvider()
    }

    private fun createViewModel(
        itemId: Int? = null,
        imagePath: String? = null
    ): SaveItemViewModel {
        return SaveItemViewModel(
            saveItemUseCase = saveItemUseCase,
            getItemByIdUseCase = getItemByIdUseCase,
            deleteItemUseCase = deleteItemUseCase,
            savedStateHandle = SavedStateHandle(),
            userSettings = userSettings,
            itemValidator = itemValidator,
            clock = testClock,
            currentDateProvider = currentDateProvider,
            itemId = itemId,
            imagePath = imagePath
        )
    }

    @Test
    fun `init with null itemId creates new item and transitions to Success immediately`() =
        runTest {
            val viewModel = createViewModel(itemId = null)

            viewModel.uiState.test {
                val state = awaitItem()

                assertIs<SaveItemUiState.Success>(state)
                assertEquals(false, state.isEditingMode)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `init with valid itemId loads data from DB and updates SavedStateHandle`() = runTest {
        val mockItem = testItem(name = "loaded name")
        coEvery { getItemByIdUseCase(mockItem.id) } returns Result.Success(mockItem)

        val viewModel = createViewModel(mockItem.id)

        viewModel.name.test {
            assertEquals("loaded name", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `process change currency command updates state and settings`() = runTest {
        val viewModel = createViewModel(itemId = null)

        viewModel.uiState.test {
            val initialState = awaitItem()
            assertIs<SaveItemUiState.Success>(initialState)

            viewModel.processCommand(SaveItemCommand.ChangeCurrency(Currency.GBP))

            val updatedState = awaitItem()
            assertIs<SaveItemUiState.Success>(updatedState)

            assertEquals(Currency.GBP, updatedState.currency)

            cancelAndIgnoreRemainingEvents()
        }

        userSettings.preferences.test {
            assertEquals(Currency.GBP, awaitItem().currency)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `process SaveItem command on success calls usecase and sends Navigate event`() = runTest {
        coEvery {
            saveItemUseCase(any(), any(), any(), any(), any(), any(), any(), any(), any())
        } returns Result.Success(Unit)

        val viewModel = createViewModel(itemId = null)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        viewModel.events.test {
            val expectedName = "Sony PlayStation 5"
            viewModel.processCommand(SaveItemCommand.ChangeName(expectedName))
            viewModel.processCommand(SaveItemCommand.SaveItem)

            assertEquals(SaveItemEvent.NavigateToPreviousScreen, awaitItem())

            coVerify(exactly = 1) {
                saveItemUseCase(
                    itemId = null,
                    name = eq(expectedName),
                    description = any(),
                    price = any(),
                    currency = any(),
                    createdAt = any(),
                    dateOfPurchase = any(),
                    imageUriString = any(),
                    originalImageLocalPath = any()
                )
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `process SaveItem command on failure sends ShowErrors event`() = runTest {
        val expectedErrors = listOf(UnexpectedError(IllegalStateException("DB Error")))

        coEvery {
            saveItemUseCase(any(), any(), any(), any(), any(), any(), any(), any(), any())
        } returns Result.Error(expectedErrors)

        val viewModel = createViewModel(itemId = null)

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        viewModel.events.test {
            viewModel.processCommand(SaveItemCommand.SaveItem)

            val event = awaitItem()
            assertIs<SaveItemEvent.ShowErrors>(event)
            assertEquals(expectedErrors, event.errors)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `process RemoveItem command calls deleteUseCase and sends Navigate event`() = runTest {
        coEvery { deleteItemUseCase(any(), any()) } returns Result.Success(Unit)
        coEvery { getItemByIdUseCase(99) } returns Result.Success(testItem(id = 99))

        val viewModel = createViewModel(itemId = 99, imagePath = "path/to/img")

        viewModel.events.test {
            viewModel.processCommand(SaveItemCommand.RemoveItem)

            assertEquals(SaveItemEvent.NavigateToPreviousScreen, awaitItem())

            coVerify(exactly = 1) {
                deleteItemUseCase(itemId = 99, itemLocalImagePath = "path/to/img")
            }

            cancelAndIgnoreRemainingEvents()
        }
    }
}
