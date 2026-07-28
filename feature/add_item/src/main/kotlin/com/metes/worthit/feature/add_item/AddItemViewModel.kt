package com.metes.worthit.feature.add_item

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.core.core_ui.UiText
import com.metes.worthit.core.core_ui.toUiText
import com.metes.worthit.core.data.utils.CurrentDateProvider
import com.metes.worthit.core.datastore.UserSettings
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Currency.Companion.fromNameOrDefault
import com.metes.worthit.core.domain.usecase.InsertItemUseCase
import com.metes.worthit.core.domain.utils.Result
import com.metes.worthit.data.common.combine
import com.metes.worthit.feature.add_item.AddItemEvent.NavigateToItems
import com.metes.worthit.feature.add_item.AddItemEvent.ShowSnackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
private const val KEY_IMAGE_URI = "item_image"
private const val KEY_BOUGHT_DATE_MILLIS = "item_bought_date"
private const val KEY_HAS_ATTEMPTED_SAVE = "has_attempted_save"

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val insertItemUseCase: InsertItemUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val userSettings: UserSettings,
    private val clock: Clock,
    currentDateProvider: CurrentDateProvider,
) : ViewModel() {

    private val hasAttemptedSaveFlow = savedStateHandle.getStateFlow(KEY_HAS_ATTEMPTED_SAVE, false)
    private val nameFlow = savedStateHandle.getStateFlow(KEY_NAME, "")
    private val priceFlow = savedStateHandle.getStateFlow(KEY_PRICE, "")
    private val imageUriFlow = savedStateHandle.getStateFlow<Uri?>(KEY_IMAGE_URI, null)
    private val descriptionFlow = savedStateHandle.getStateFlow(KEY_DESCRIPTION, "")
    private val isSavingFlow = MutableStateFlow(false)

    private val initialSelectedDateMillis = LocalDate.now(clock)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant()
        .toEpochMilli()
    private val boughtDateMillisFlow = savedStateHandle.getStateFlow<Long?>(
        KEY_BOUGHT_DATE_MILLIS, initialSelectedDateMillis
    )
    private val currencyFlow = userSettings.getCurrencyName().map { currencyName ->
        fromNameOrDefault(currencyName)
    }

    val uiState = combine(
        nameFlow,
        imageUriFlow,
        descriptionFlow,
        currencyFlow,
        priceFlow,
        boughtDateMillisFlow,
        hasAttemptedSaveFlow,
        currentDateProvider.currentDate
    ) { name, imageUri, description, currency, price, boughtDateMillis, hasAttemptedSave, currentDate ->
        AddItemUiState.Success(
            name = name,
            price = price,
            description = description,
            imageUri = imageUri,
            currency = currency,
            boughtDateMillis = boughtDateMillis,
            currentDate = currentDate,
            isValidName = name.isNotBlank() || (name.isBlank() && !hasAttemptedSave),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddItemUiState.Loading
    )

    private val _events = Channel<AddItemEvent>()
    val events = _events.receiveAsFlow()

    fun processCommand(command: AddItemCommand) {
        when (command) {
            is AddItemCommand.AddItem -> {
                addItem()
            }

            is AddItemCommand.ChangeName -> {
                savedStateHandle[KEY_NAME] = command.name
            }

            is AddItemCommand.ChangePrice -> {
                savedStateHandle[KEY_PRICE] = command.price
            }

            is AddItemCommand.SelectImage -> {
                savedStateHandle[KEY_IMAGE_URI] = command.uri
            }

            is AddItemCommand.ChangeDescription -> {
                savedStateHandle[KEY_DESCRIPTION] = command.description
            }

            is AddItemCommand.ChangeCurrency -> {
                viewModelScope.launch {
                    userSettings.saveCurrency(command.currency)
                }
            }

            is AddItemCommand.SelectBoughtDate -> {
                savedStateHandle[KEY_BOUGHT_DATE_MILLIS] = command.boughtDateMillis
            }

            AddItemCommand.RemoveImage -> {
                savedStateHandle[KEY_IMAGE_URI] = null
            }

            AddItemCommand.RemoveName -> {
                savedStateHandle[KEY_NAME] = ""
            }

            AddItemCommand.RemoveDescription -> {
                savedStateHandle[KEY_DESCRIPTION] = ""
            }
        }
    }

    private fun addItem() {
        if (isSavingFlow.value) return

        val currentState = uiState.value
        if (currentState is AddItemUiState.Success) {
            if (currentState.name.isBlank()) {
                savedStateHandle[KEY_HAS_ATTEMPTED_SAVE] = true
                viewModelScope.launch {
                    _events.send(
                        ShowSnackbar(
                            message = UiText.StringResource(R.string.form_is_incorrect),
                            isError = true
                        )
                    )
                }
                return
            }

            viewModelScope.launch {
                isSavingFlow.value = true

                try {
                    val priceLong = currentState.price.toLongOrNull()
                    val createdAtInstant = Instant.now(clock)
                    val boughtAtDate = currentState.boughtDateMillis?.let { utcMillis ->
                        Instant.ofEpochMilli(utcMillis)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()
                    }

                    val result = insertItemUseCase(
                        name = currentState.name,
                        description = currentState.description,
                        price = priceLong,
                        currency = currentState.currency,
                        createdAt = createdAtInstant,
                        boughtAt = boughtAtDate,
                        imageUriString = currentState.imageUri?.toString()
                    )

                    when (result) {
                        is Result.Error<Exception> -> {
                            _events.send(
                                ShowSnackbar(
                                    message = result.error.toUiText(),
                                    isError = true
                                )
                            )
                        }

                        is Result.Success<Unit> -> {
                            _events.send(NavigateToItems)
                        }
                    }
                } finally {
                    isSavingFlow.value = false
                }
            }
        }
    }
}

sealed interface AddItemCommand {
    data object AddItem : AddItemCommand
    data class ChangeName(val name: String) : AddItemCommand
    data object RemoveName : AddItemCommand
    data class ChangePrice(val price: String) : AddItemCommand
    data object RemoveDescription : AddItemCommand
    data class ChangeDescription(val description: String) : AddItemCommand
    data class SelectImage(val uri: Uri) : AddItemCommand
    data class SelectBoughtDate(val boughtDateMillis: Long) : AddItemCommand
    data object RemoveImage : AddItemCommand
    data class ChangeCurrency(val currency: Currency) : AddItemCommand
}

sealed interface AddItemEvent {
    data object NavigateToItems : AddItemEvent
    data class ShowSnackbar(val message: UiText, val isError: Boolean) : AddItemEvent
}

sealed interface AddItemUiState {
    data object Loading : AddItemUiState
    data class Success(
        val name: String,
        val price: String,
        val description: String,
        val imageUri: Uri?,
        val currency: Currency,
        val boughtDateMillis: Long?,
        val currentDate: LocalDate,
        val isValidName: Boolean
    ) : AddItemUiState
}