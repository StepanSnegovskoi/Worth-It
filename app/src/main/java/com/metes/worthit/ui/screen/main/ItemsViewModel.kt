package com.metes.worthit.ui.screen.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.domain.entity.Item
import com.metes.worthit.domain.usecase.InsertItemUseCase
import com.metes.worthit.domain.usecase.ObserveItemsUseCase
import com.metes.worthit.ui.screen.add_item.AddItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val observeItemsUseCase: ObserveItemsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiState = observeItemsUseCase().map { items ->
        ItemsState(items = items)
    }.stateIn(
        scope = viewModelScope,
        initialValue = ItemsState(),
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
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

data class ItemsState(
    val items: List<Item> = INITIAL_ITEMS
) {
    companion object {
        private val INITIAL_ITEMS = emptyList<Item>()
    }
}