package com.metes.worthit.ui.screen.add_item

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.app.DispatcherProvider
import com.metes.worthit.app.StandardDispatchers
import com.metes.worthit.domain.usecase.InsertItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val insertItemUseCase: InsertItemUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val nameQuery = savedStateHandle.getStateFlow(KEY_NAME, INITIAL_NAME)
    private val imageUri = savedStateHandle.getStateFlow(KEY_IMAGE_URI, INITIAL_IMAGE_URI)

    val uiState = combine(
        nameQuery, imageUri
    ) { name: String, imageUri: Uri? ->
        AddItemState(name = name, imageUri = imageUri)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddItemState(name = INITIAL_NAME, imageUri = INITIAL_IMAGE_URI)
    )

    private val _events = Channel<AddItemEvent>()
    val events = _events.receiveAsFlow()

    fun processCommand(command: AddItemCommand) {
        when(command) {
            is AddItemCommand.AddItem -> {
                viewModelScope.launch {
                    val name = uiState.value.name
                    insertItemUseCase(name)
                    _events.send(AddItemEvent.NavigateToItems)
                }
            }

            is AddItemCommand.ChangeName -> {
                savedStateHandle[KEY_NAME] = command.name
            }

            is AddItemCommand.SelectImage -> {
                savedStateHandle[KEY_IMAGE_URI] = command.uri
            }
        }
    }

    companion object {
        private const val KEY_NAME = "name"
        private const val INITIAL_NAME = ""
        private const val KEY_IMAGE_URI = "image"
        private val INITIAL_IMAGE_URI: Uri? = null
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
    val name: String,
    val imageUri: Uri?
)