package com.metes.worthit.ui.screen.add_item

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.R
import com.metes.worthit.domain.usecase.InsertItemUseCase
import com.metes.worthit.domain.utils.Result
import com.metes.worthit.ui.entity.UiText
import com.metes.worthit.ui.entity.UiText.*
import com.metes.worthit.ui.screen.add_item.AddItemEvent.*
import com.metes.worthit.ui.utils.toUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

const val KEY_NAME = "item_name"
const val KEY_DESCRIPTION = "item_description"
const val KEY_IMAGE_URI = "item_image"

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val insertItemUseCase: InsertItemUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val nameFlow = savedStateHandle.getStateFlow(KEY_NAME, "")
    private val imageUriFlow = savedStateHandle.getStateFlow<Uri?>(KEY_IMAGE_URI, null)
    private val descriptionFlow = savedStateHandle.getStateFlow(KEY_DESCRIPTION, "")

    val uiState = combine(
        nameFlow, imageUriFlow, descriptionFlow
    ) { name: String, imageUri: Uri?, description: String ->
        AddItemState(name = name, description = description, imageUri = imageUri)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AddItemState(name = "", description = "", imageUri = null)
    )

    private val _events = Channel<AddItemEvent>()
    val events = _events.receiveAsFlow()

    fun processCommand(command: AddItemCommand) {
        when (command) {
            is AddItemCommand.AddItem -> {
                viewModelScope.launch {
                    val name = uiState.value.name
                    val imageUri = uiState.value.imageUri
                    val description = uiState.value.description

                    val result =
                        insertItemUseCase(name = name, description = description, imageUriString = imageUri?.toString())
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

            is AddItemCommand.ChangeName -> {
                savedStateHandle[KEY_NAME] = command.name
            }

            is AddItemCommand.SelectImage -> {
                savedStateHandle[KEY_IMAGE_URI] = command.uri
            }

            is AddItemCommand.ChangeDescription -> {
                savedStateHandle[KEY_DESCRIPTION] = command.description
            }
        }
    }
}

sealed interface AddItemCommand {
    data object AddItem : AddItemCommand
    data class ChangeName(val name: String) : AddItemCommand
    data class ChangeDescription(val description: String) : AddItemCommand
    data class SelectImage(val uri: Uri) : AddItemCommand
}

sealed interface AddItemEvent {
    data object NavigateToItems : AddItemEvent
    data class ShowToast(val message: UiText) : AddItemEvent
}

data class AddItemState(
    val name: String,
    val description: String,
    val imageUri: Uri?
)