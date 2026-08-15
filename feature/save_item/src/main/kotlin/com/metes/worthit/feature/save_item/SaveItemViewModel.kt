@file:OptIn(ExperimentalCoroutinesApi::class)

package com.metes.worthit.feature.save_item

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Currency.Companion.fromNameOrDefault
import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.usecase.GetItemByIdUseCase
import com.metes.worthit.core.domain.usecase.SaveItemUseCase
import com.metes.worthit.core.domain.utils.DateProvider
import com.metes.worthit.core.domain.utils.Result
import com.metes.worthit.core.domain.validator.ItemValidator
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.core.common.toEpochMilliOrNull
import com.metes.worthit.core.domain.utils.UserSettings
import com.metes.worthit.core.domain.utils.onError
import com.metes.worthit.core.domain.utils.onSuccess
import com.metes.worthit.feature.save_item.SaveItemEvent.NavigateToItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

private const val KEY_NAME = "item_name"
private const val KEY_PRICE = "item_price"
private const val KEY_DESCRIPTION = "item_description"
private const val KEY_IMAGE_PATH = "item_image_path"
private const val KEY_BOUGHT_DATE_MILLIS = "item_bought_date"
private const val KEY_HAS_ATTEMPTED_SAVE = "has_attempted_save"

@HiltViewModel
class SaveItemViewModel @Inject constructor(
    private val saveItemUseCase: SaveItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val userSettings: UserSettings,
    private val itemValidator: ItemValidator,
    private val clock: Clock,
    currentDateProvider: DateProvider,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Screen.SaveItem>()

    private val initialSelectedDateMillis = LocalDate.now(clock).toEpochMilliOrNull()

    private val initialImagePath = args.imagePath

    private val isEditingMode = args.itemId != null

    private val hasAttemptedSaveFlow = savedStateHandle.getStateFlow(KEY_HAS_ATTEMPTED_SAVE, false)
    private val nameFlow = savedStateHandle.getStateFlow(KEY_NAME, "")
    private val priceFlow = savedStateHandle.getStateFlow(KEY_PRICE, "")
    private val imageUriFlow =
        savedStateHandle.getStateFlow(KEY_IMAGE_PATH, initialImagePath?.toUri())
    private val descriptionFlow = savedStateHandle.getStateFlow(KEY_DESCRIPTION, "")
    private val dateOfPurchaseMillisFlow = savedStateHandle.getStateFlow(
        KEY_BOUGHT_DATE_MILLIS, initialSelectedDateMillis
    )
    private val currencyFlow = userSettings.getCurrencyName().map { currencyName ->
        fromNameOrDefault(currencyName)
    }
    private val isSavingFlow = MutableStateFlow(false)
    private val isInitializingFlow = MutableStateFlow(true)

    private val userInputFlow = combine(
        nameFlow,
        priceFlow,
        descriptionFlow,
        imageUriFlow,
    ) { name, price, description, imageUri ->
        UserInputData(name, price, description, imageUri)
    }

    private val metaDataFlow = combine(
        currencyFlow,
        dateOfPurchaseMillisFlow,
        hasAttemptedSaveFlow,
        currentDateProvider.currentDateFlow,
    ) { currency, dateOfPurchaseMillis, hasAttemptedSave, currentDate ->
        MetaData(currency, dateOfPurchaseMillis, hasAttemptedSave, currentDate)
    }

    private val formStateFlow = combine(userInputFlow, metaDataFlow) { userInput, metadata ->
        val isValidName =
            itemValidator.validateName(userInput.name) is Result.Success || !metadata.hasAttemptedSave

        val isValidPrice =
            itemValidator.validatePrice(userInput.price) is Result.Success || !metadata.hasAttemptedSave

        SaveItemUiState.Success(
            name = userInput.name,
            price = userInput.price,
            description = userInput.description,
            imageUri = userInput.imageUri,
            currency = metadata.currency,
            dateOfPurchaseMillis = metadata.dateOfPurchaseMillis,
            currentDate = metadata.currentDate,
            isValidName = isValidName,
            isValidPrice = isValidPrice,
            isEditingMode = isEditingMode
        )
    }

    val uiState = isInitializingFlow.flatMapLatest { isInitializing ->
        when (isInitializing) {
            true -> flowOf(SaveItemUiState.Loading)
            false -> formStateFlow
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SaveItemUiState.Loading
    )

    private val _events = Channel<SaveItemEvent>()
    val events = _events.receiveAsFlow()

    init {
        setItem(args.itemId)
    }

    private fun setItem(itemId: Int?) {
        viewModelScope.launch {
            try {
                if (itemId == null) return@launch

                when (val result = getItemByIdUseCase(itemId)) {
                    is Result.Error<List<Error>> -> {
                        _events.send(SaveItemEvent.ShowErrors(result.data))
                    }

                    is Result.Success<Item> -> {
                        val item = result.data

                        savedStateHandle[KEY_NAME] = item.name
                        savedStateHandle[KEY_DESCRIPTION] = item.description
                        savedStateHandle[KEY_PRICE] = item.price?.toString() ?: ""
                        savedStateHandle[KEY_IMAGE_PATH] = item.imageLocalPath?.toUri()
                        savedStateHandle[KEY_BOUGHT_DATE_MILLIS] =
                            item.dateOfPurchase.toEpochMilliOrNull()
                    }
                }
            } finally {
                isInitializingFlow.value = false
            }
        }
    }

    fun processCommand(command: SaveItemCommand) {
        when (command) {
            is SaveItemCommand.SaveItem -> {
                saveItem()
            }

            is SaveItemCommand.ChangeName -> {
                savedStateHandle[KEY_NAME] = command.name
            }

            is SaveItemCommand.ChangePrice -> {
                savedStateHandle[KEY_PRICE] = command.price
            }

            is SaveItemCommand.SelectImage -> {
                savedStateHandle[KEY_IMAGE_PATH] = command.uri
            }

            is SaveItemCommand.ChangeDescription -> {
                savedStateHandle[KEY_DESCRIPTION] = command.description
            }

            is SaveItemCommand.ChangeCurrency -> {
                viewModelScope.launch {
                    userSettings.saveCurrency(command.currency)
                }
            }

            is SaveItemCommand.SelectBoughtDate -> {
                savedStateHandle[KEY_BOUGHT_DATE_MILLIS] = command.boughtDateMillis
            }

            SaveItemCommand.RemoveImage -> {
                savedStateHandle[KEY_IMAGE_PATH] = null
            }

            SaveItemCommand.RemoveName -> {
                savedStateHandle[KEY_NAME] = ""
            }

            SaveItemCommand.RemoveDescription -> {
                savedStateHandle[KEY_DESCRIPTION] = ""
            }
        }
    }

    private fun saveItem() {
        savedStateHandle[KEY_HAS_ATTEMPTED_SAVE] = true

        if (isSavingFlow.value) return

        val currentState = uiState.value
        if (currentState !is SaveItemUiState.Success) return

        isSavingFlow.value = true

        viewModelScope.launch {
            val createdAtInstant = Instant.now(clock)
            val dateOfPurchase = currentState.dateOfPurchaseMillis.let { utcMillis ->
                Instant.ofEpochMilli(utcMillis)
                    .atZone(ZoneOffset.UTC)
                    .toLocalDate()
            }

            val result = saveItemUseCase(
                itemId = args.itemId,
                name = currentState.name,
                description = currentState.description,
                price = currentState.price,
                currency = currentState.currency,
                createdAt = createdAtInstant,
                dateOfPurchase = dateOfPurchase,
                imageUriString = currentState.imageUri?.toString(),
                originalImageLocalPath = initialImagePath
            )

            when (result) {
                is Result.Error<List<Error>> -> {
                    _events.send(SaveItemEvent.ShowErrors(result.data))
                    isSavingFlow.value = false
                }

                is Result.Success<Unit> -> {
                    _events.send(NavigateToItems)
                }
            }
        }
    }
}

sealed interface SaveItemCommand {
    data object SaveItem : SaveItemCommand
    data class ChangeName(val name: String) : SaveItemCommand
    data object RemoveName : SaveItemCommand
    data class ChangePrice(val price: String) : SaveItemCommand
    data object RemoveDescription : SaveItemCommand
    data class ChangeDescription(val description: String) : SaveItemCommand
    data class SelectImage(val uri: Uri) : SaveItemCommand
    data class SelectBoughtDate(val boughtDateMillis: Long) : SaveItemCommand
    data object RemoveImage : SaveItemCommand
    data class ChangeCurrency(val currency: Currency) : SaveItemCommand
}

sealed interface SaveItemEvent {
    data object NavigateToItems : SaveItemEvent
    data class ShowErrors(val errors: List<Error>) : SaveItemEvent
}

@Immutable
sealed interface SaveItemUiState {
    data object Loading : SaveItemUiState
    data class Success(
        val name: String,
        val price: String,
        val description: String,
        val imageUri: Uri?,
        val currency: Currency,
        val dateOfPurchaseMillis: Long,
        val currentDate: LocalDate,
        val isValidName: Boolean,
        val isValidPrice: Boolean,
        val isEditingMode: Boolean,
    ) : SaveItemUiState
}

private data class UserInputData(
    val name: String,
    val price: String,
    val description: String,
    val imageUri: Uri?,
)

private data class MetaData(
    val currency: Currency,
    val dateOfPurchaseMillis: Long,
    val hasAttemptedSave: Boolean,
    val currentDate: LocalDate,
)
