package com.metes.worthit.ui.screen.add_item

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.domain.usecase.InsertItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val insertItemUseCase: InsertItemUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddItemState>(AddItemState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<AddItemEvent>()
    val events = _events.receiveAsFlow()

    fun processCommand(command: AddItemCommand) {
        when(command) {
            is AddItemCommand.AddItem -> {
                viewModelScope.launch {
                    val name = _uiState.value.name
                    insertItemUseCase(name = name)
                    _events.send(AddItemEvent.NavigateToItems)
                }
            }

            is AddItemCommand.ChangeName -> {
                val previousState = _uiState.value
                _uiState.value = previousState.copy(name = command.name)
            }

            is AddItemCommand.SelectImage -> {
                val previousState = _uiState.value
                _uiState.value = previousState.copy(imageUri = command.uri)
            }
        }
    }
}

sealed interface AddItemCommand {
    data object AddItem: AddItemCommand
    data class ChangeName(val name: String): AddItemCommand
    data class SelectImage(val uri: Uri): AddItemCommand
}

sealed interface AddItemEvent {
    data object NavigateToItems: AddItemEvent
}

data class AddItemState(
    val name: String = INITIAL_NAME,
    val imageUri: Uri? = INITIAL_IMAGE_URI
) {
    companion object {
        private const val INITIAL_NAME = ""
        private val INITIAL_IMAGE_URI = null
    }
}