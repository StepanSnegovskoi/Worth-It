package com.metes.worthit.ui.screen.add_item

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.R
import com.metes.worthit.app.UserSettings
import com.metes.worthit.domain.usecase.InsertItemUseCase
import com.metes.worthit.domain.utils.Result
import com.metes.worthit.ui.entity.Currency
import com.metes.worthit.ui.entity.UiText
import com.metes.worthit.ui.entity.UiText.StringResource
import com.metes.worthit.ui.screen.add_item.AddItemEvent.NavigateToItems
import com.metes.worthit.ui.screen.add_item.AddItemEvent.ShowToast
import com.metes.worthit.ui.utils.toUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val KEY_NAME = "item_name"
private const val KEY_PRICE = "item_price"
private const val KEY_DESCRIPTION = "item_description"
private const val KEY_IMAGE_URI = "item_image"

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val insertItemUseCase: InsertItemUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val userSettings: UserSettings,
) : ViewModel() {

    private val nameFlow = savedStateHandle.getStateFlow(KEY_NAME, "")
    private val priceFlow = savedStateHandle.getStateFlow(KEY_PRICE, "")
    private val imageUriFlow = savedStateHandle.getStateFlow<Uri?>(KEY_IMAGE_URI, null)
    private val descriptionFlow = savedStateHandle.getStateFlow(KEY_DESCRIPTION, "")
    private val currencyNameFlow = userSettings.getCurrencyName().map { currencyName ->
        Currency.valueOf(currencyName)
    }

    val uiState = combine(
        nameFlow, imageUriFlow, descriptionFlow, currencyNameFlow, priceFlow
    ) { name: String, imageUri: Uri?, description: String, currency: Currency, price: String ->
        AddItemUiState.Success(
            name = name,
            price = price,
            description = description,
            imageUri = imageUri,
            currency = currency
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
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
                savedStateHandle[KEY_PRICE] = command.name
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
        }
    }

    private fun addItem() {
        viewModelScope.launch {
            val currentState = uiState.value
            if (currentState is AddItemUiState.Success) {
                val name = currentState.name
                val imageUri = currentState.imageUri
                val description = currentState.description

                val result = insertItemUseCase(
                    name = name,
                    description = description,
                    imageUriString = imageUri?.toString()
                )

                when (result) {
                    is Result.Error<Exception> -> {
                        _events.send(ShowToast(result.error.toUiText()))
                    }

                    is Result.Success<*> -> {
                        _events.send(ShowToast(StringResource(R.string.item_created)))
                        _events.send(NavigateToItems)
                    }
                }
            }
        }
    }
}

sealed interface AddItemCommand {
    data object AddItem : AddItemCommand
    data class ChangeName(val name: String) : AddItemCommand
    data class ChangePrice(val name: String) : AddItemCommand
    data class ChangeDescription(val description: String) : AddItemCommand
    data class SelectImage(val uri: Uri) : AddItemCommand
    data class ChangeCurrency(val currency: Currency) : AddItemCommand
}

sealed interface AddItemEvent {
    data object NavigateToItems : AddItemEvent
    data class ShowToast(val message: UiText) : AddItemEvent
}

sealed interface AddItemUiState {
    data object Loading : AddItemUiState

    data class Success(
        val name: String,
        val price: String,
        val description: String,
        val imageUri: Uri?,
        val currency: Currency
    ) : AddItemUiState
}