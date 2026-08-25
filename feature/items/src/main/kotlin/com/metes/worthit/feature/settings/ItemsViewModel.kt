package com.metes.worthit.feature.settings

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.core.domain.usecase.DeleteItemUseCase
import com.metes.worthit.core.domain.usecase.ObserveItemsUseCase
import com.metes.worthit.feature.settings.mapper.toUiModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val deleteItemUseCase: DeleteItemUseCase,
    observeItemsUseCase: ObserveItemsUseCase,
) : ViewModel() {

    private val _selectedItemIds = MutableStateFlow<Set<Int>>(emptySet())

    val uiState = combine(
        observeItemsUseCase(),
        _selectedItemIds,
    ) { items, selectedItemIds ->
        val uiItems = items.toUiModels()
        ItemsUiState.Success(items = uiItems, selectedItemIds = selectedItemIds)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = ItemsUiState.Loading
    )

    private val _events = Channel<ItemsEvent>()
    val events = _events.receiveAsFlow()

    fun processCommand(command: ItemsCommand) {
        when (command) {
            is ItemsCommand.DeleteItem -> {
                viewModelScope.launch {
                    deleteItemUseCase(command.itemId, command.itemLocalImagePath)
                }
            }

            is ItemsCommand.ClickItem -> {
                viewModelScope.launch {
                    _events.send(ItemsEvent.NavigateToSaveItem(command.itemId))
                }
            }

            is ItemsCommand.LongClickItem -> {
                val currentState = uiState.value as? ItemsUiState.Success ?: return

                if (command.itemId in currentState.selectedItemIds) {
                    _selectedItemIds.value -= command.itemId
                } else {
                    _selectedItemIds.value += command.itemId
                }
            }
        }
    }
}

sealed interface ItemsCommand {
    data class DeleteItem(val itemId: Int, val itemLocalImagePath: String?) : ItemsCommand
    data class ClickItem(val itemId: Int) : ItemsCommand
    data class LongClickItem(val itemId: Int) : ItemsCommand
}

sealed interface ItemsEvent {
    data class NavigateToSaveItem(val itemId: Int) : ItemsEvent
}

@Immutable
sealed interface ItemsUiState {
    data object Loading : ItemsUiState

    data class Success(
        val items: List<ItemUiModel>,
        val selectedItemIds: Set<Int>,
    ) : ItemsUiState
}

@Immutable
data class ItemUiModel(
    val id: Int,
    val name: String,
    val localImagePath: String?,
    val dateOfPurchase: LocalDate,
)