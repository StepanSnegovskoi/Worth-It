package com.metes.worthit.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.data.utils.CurrentDateProvider
import com.metes.worthit.domain.usecase.DeleteItemUseCase
import com.metes.worthit.domain.usecase.ObserveItemsUseCase
import com.metes.worthit.ui.screen.main.mapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val observeItemsUseCase: ObserveItemsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val currentDateProvider: CurrentDateProvider
) : ViewModel() {

    val uiState =
        combine(observeItemsUseCase(), currentDateProvider.currentDate) { items, currentDate ->
            val uiItems = items.map { it.toUiModel(currentDate) }
            ItemsUiState.Success(uiItems = uiItems)
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

            is ItemsCommand.DeleteItem -> {
                viewModelScope.launch {
                    deleteItemUseCase(command.itemId, command.itemLocalImagePath)
                }
            }
        }
    }
}

sealed interface ItemsCommand {
    data object AddItem : ItemsCommand
    data class DeleteItem(val itemId: Int, val itemLocalImagePath: String?) : ItemsCommand
}

sealed interface ItemsEvent {
    data object NavigateToAddItem : ItemsEvent
}

sealed interface ItemsUiState {
    data object Loading : ItemsUiState

    data class Success(
        val uiItems: List<ItemUiModel>
    ) : ItemsUiState
}

data class ItemUiModel(
    val id: Int,
    val name: String,
    val localImagePath: String?,
    val formattedDates: String?,
    val daysCountText: String?,
    val pricePerDayText: String?
)