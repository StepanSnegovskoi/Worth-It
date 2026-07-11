package com.metes.worthit.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.domain.entity.Item
import com.metes.worthit.domain.usecase.ObserveItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val observeItemsUseCase: ObserveItemsUseCase
) : ViewModel() {

    val uiState = observeItemsUseCase().map { items ->
        ItemsUiState.Success(items = items)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = ItemsUiState.Loading
    )

    private val _events = Channel<ItemsEvent>()
    val events = _events.receiveAsFlow()

    fun processCommand(command: ItemsCommand) {
        when (command) {
            ItemsCommand.AddItem -> {
                viewModelScope.launch {
                    _events.send(ItemsEvent.NavigateToAddItem)
                }
            }
        }
    }
}

sealed interface ItemsCommand {
    data object AddItem : ItemsCommand
}

sealed interface ItemsEvent {
    data object NavigateToAddItem : ItemsEvent
}

sealed interface ItemsUiState {
    data object Loading : ItemsUiState

    data class Success(
        val items: List<Item>
    ) : ItemsUiState
}