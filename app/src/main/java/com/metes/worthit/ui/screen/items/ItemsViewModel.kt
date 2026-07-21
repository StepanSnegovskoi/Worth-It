package com.metes.worthit.ui.screen.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.data.utils.CurrentDateProvider
import com.metes.worthit.domain.usecase.DeleteItemUseCase
import com.metes.worthit.domain.usecase.ObserveItemsUseCase
import com.metes.worthit.ui.screen.items.mapper.toUiModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val observeItemsUseCase: ObserveItemsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val currentDateProvider: CurrentDateProvider
) : ViewModel() {

    val uiState =
        combine(observeItemsUseCase(), currentDateProvider.currentDate) { items, currentDate ->
            val uiItems = items.toUiModels(currentDate)
            ItemsUiState.Success(uiItems = uiItems, currentDate = currentDate)
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
        }
    }
}

sealed interface ItemsCommand {
    data class DeleteItem(val itemId: Int, val itemLocalImagePath: String?) : ItemsCommand
}

sealed interface ItemsEvent {
}

sealed interface ItemsUiState {
    data object Loading : ItemsUiState

    data class Success(
        val uiItems: List<ItemUiModel>,
        val currentDate: LocalDate
    ) : ItemsUiState
}

data class ItemUiModel(
    val id: Int,
    val name: String,
    val localImagePath: String?,
    val boughtAt: LocalDate?,
    val daysCount: Long?,
    val pricePerDay: Double?
)