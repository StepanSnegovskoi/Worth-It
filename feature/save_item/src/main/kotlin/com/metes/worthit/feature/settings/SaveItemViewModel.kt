@file:OptIn(ExperimentalCoroutinesApi::class)

package com.metes.worthit.feature.settings

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.core.common.toLocalDateFromUtc
import com.metes.worthit.core.common.toUtcEpochMilli
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Currency.Companion.fromNameOrDefault
import com.metes.worthit.core.domain.entity.TimeUnit
import com.metes.worthit.core.domain.entity.calculatePrice
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.usecase.GetItemByIdUseCase
import com.metes.worthit.core.domain.usecase.SaveItemUseCase
import com.metes.worthit.core.domain.utils.DateProvider
import com.metes.worthit.core.domain.utils.Result
import com.metes.worthit.core.domain.utils.UserSettings
import com.metes.worthit.core.domain.utils.onError
import com.metes.worthit.core.domain.utils.onSuccess
import com.metes.worthit.core.domain.validator.ItemValidator
import com.metes.worthit.feature.settings.SaveItemEvent.NavigateToItems
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
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

private const val KEY_NAME = "item_name"
private const val KEY_PRICE = "item_price"
private const val KEY_DESCRIPTION = "item_description"
private const val KEY_IMAGE_PATH = "item_image_path"
private const val KEY_BOUGHT_DATE_MILLIS = "item_bought_date"
private const val KEY_HAS_ATTEMPTED_SAVE = "has_attempted_save"

@HiltViewModel(assistedFactory = SaveItemViewModel.Factory::class)
class SaveItemViewModel @AssistedInject constructor(
    private val saveItemUseCase: SaveItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val userSettings: UserSettings,
    private val itemValidator: ItemValidator,
    private val clock: Clock,
    currentDateProvider: DateProvider,
    @Assisted private val itemId: Int? = null,
    @Assisted private val imagePath: String? = null,
) : ViewModel() {

    private val isEditingMode = itemId != null
    private val initialSelectedDateMillis = LocalDate.now(clock).toUtcEpochMilli()

    private val isInitializingFlow = MutableStateFlow(true)
    private val isSavingFlow = MutableStateFlow(false)

    private val hasAttemptedSaveFlow = savedStateHandle.getStateFlow(KEY_HAS_ATTEMPTED_SAVE, false)
    private val nameFlow = savedStateHandle.getStateFlow(KEY_NAME, "")
    private val priceFlow = savedStateHandle.getStateFlow(KEY_PRICE, "")
    private val descriptionFlow = savedStateHandle.getStateFlow(KEY_DESCRIPTION, "")
    private val imageUriFlow = savedStateHandle.getStateFlow(KEY_IMAGE_PATH, imagePath?.toUri())
    private val dateOfPurchaseMillisFlow = savedStateHandle.getStateFlow(
        key = KEY_BOUGHT_DATE_MILLIS,
        initialValue = initialSelectedDateMillis
    )

    private val currencyFlow = userSettings.getCurrencyName()
        .map { currency -> fromNameOrDefault(currency) }

    private val userInputFlow = combine(
        nameFlow,
        priceFlow,
        descriptionFlow,
        imageUriFlow,
        ::UserInputData
    )

    private val metaDataFlow = combine(
        currencyFlow,
        dateOfPurchaseMillisFlow,
        hasAttemptedSaveFlow,
        currentDateProvider.currentDateFlow,
        ::MetaData
    )

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
            isEditingMode = isEditingMode,
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
        setItem(itemId)
    }

    private fun setItem(itemId: Int?) {
        viewModelScope.launch {
            try {
                if (itemId == null) return@launch
                val result = getItemByIdUseCase(itemId)

                result.onError { errors ->
                    _events.send(SaveItemEvent.ShowErrors(errors))
                }.onSuccess { item ->
                    savedStateHandle[KEY_NAME] = item.name
                    savedStateHandle[KEY_DESCRIPTION] = item.description
                    savedStateHandle[KEY_PRICE] = item.price?.toString() ?: ""
                    savedStateHandle[KEY_IMAGE_PATH] = item.imageLocalPath?.toUri()
                    savedStateHandle[KEY_BOUGHT_DATE_MILLIS] =
                        item.dateOfPurchase.toUtcEpochMilli()
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
                val price = itemValidator.normalizePriceInput(command.price)

                savedStateHandle[KEY_PRICE] = price
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

    // bug, when user fast clicks at this screen in bottom bar after success saving
    // the state of this screen is previous (viewmodel doesn't cleared because animation
    // didn't finish)
    private fun saveItem() {
        savedStateHandle[KEY_HAS_ATTEMPTED_SAVE] = true

        if (isSavingFlow.value) return

        val currentState = uiState.value
        if (currentState !is SaveItemUiState.Success) return

        isSavingFlow.value = true

        viewModelScope.launch {
            try {
                val createdAt = Instant.now(clock)
                val dateOfPurchase = currentState.dateOfPurchaseMillis.toLocalDateFromUtc()

                val result = saveItemUseCase(
                    itemId = itemId,
                    name = currentState.name,
                    description = currentState.description,
                    price = currentState.price,
                    currency = currentState.currency,
                    createdAt = createdAt,
                    dateOfPurchase = dateOfPurchase,
                    imageUriString = currentState.imageUri?.toString(),
                    originalImageLocalPath = imagePath
                )

                result.onError { errors ->
                    _events.send(SaveItemEvent.ShowErrors(errors))
                }.onSuccess {
                    _events.send(NavigateToItems)
                }
            } finally {
                isSavingFlow.value = false
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(
            itemId: Int? = null,
            imagePath: String? = null,
        ): SaveItemViewModel
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
    ) : SaveItemUiState {

        val pricesPerTimeUnits: List<PricePerTimeUnitModel>
            get() {
                val priceBigDecimal = price.toBigDecimalOrNull() ?: return emptyList()
                val dateOfPurchase = dateOfPurchaseMillis.toLocalDateFromUtc()

                return TimeUnit.entries.map { timeUnit ->
                    PricePerTimeUnitModel(
                        timeUnit = timeUnit,
                        amount = timeUnit.calculatePrice(
                            price = priceBigDecimal,
                            currentDate = currentDate,
                            dateOfPurchase = dateOfPurchase,
                        ).toString()
                    )
                }
            }
    }
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

data class PricePerTimeUnitModel(
    val timeUnit: TimeUnit,
    val amount: String = "",
)
