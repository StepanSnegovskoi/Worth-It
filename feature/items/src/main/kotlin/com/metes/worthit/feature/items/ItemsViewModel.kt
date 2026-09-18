package com.metes.worthit.feature.items

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.core.common.DefaultDispatcher
import com.metes.worthit.core.domain.usecase.DeleteItemUseCase
import com.metes.worthit.core.domain.usecase.DeleteItemsUseCase
import com.metes.worthit.core.domain.usecase.ObserveItemsUseCase
import com.metes.worthit.feature.items.mapper.toUiModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

private const val KEY_SEARCH_QUERY = "seach_query"

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val deleteItemUseCase: DeleteItemUseCase,
    private val deleteItemsUseCase: DeleteItemsUseCase,
    private val savedStateHandle: SavedStateHandle,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    observeItemsUseCase: ObserveItemsUseCase,
) : ViewModel() {

    private val selectedItemIds = MutableStateFlow<Set<Int>>(emptySet())
    private val searchQueryFlow = savedStateHandle.getStateFlow(KEY_SEARCH_QUERY, "")

    val uiState = combine(
        observeItemsUseCase(),
        selectedItemIds,
        searchQueryFlow,
    ) { items, selectedItemIds, searchQuery ->
        val uiItems = items.toUiModels()

        val filteredItems = uiItems.filter {
            it.name.startsWith(searchQuery, ignoreCase = true)
        }

        val filteredIds = filteredItems.map { it.id }.toSet()

        val realSelectedIds = selectedItemIds.intersect(filteredIds)

        ItemsUiState.Success(
            items = uiItems,
            filteredItemsByQuery = filteredItems,
            selectedItemIds = realSelectedIds,
            searchQuery = searchQuery,
        )
    }
        .flowOn(defaultDispatcher)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = ItemsUiState.Loading
        )

    private val _events = Channel<ItemsEvent>()
    val events = _events.receiveAsFlow()

    fun processCommand(command: ItemsCommand) {
        val currentState = uiState.value
        if (currentState !is ItemsUiState.Success) return

        when (command) {
            is ItemsCommand.DeleteItem -> {
                viewModelScope.launch {
                    deleteItemUseCase(command.itemId, command.itemLocalImagePath)
                    selectedItemIds.value -= command.itemId
                }
            }

            is ItemsCommand.ClickItem -> {
                if (currentState.selectedItemIds.isEmpty()) {
                    navigateToSaveItem(command.itemId)
                } else {
                    changeSelectedStatus(command.itemId)
                }
            }

            is ItemsCommand.LongClickItem -> {
                if (currentState.selectedItemIds.isNotEmpty()) {
                    navigateToSaveItem(command.itemId)
                } else {
                    changeSelectedStatus(command.itemId)
                }
            }

            is ItemsCommand.DeleteItems -> {
                val selectedItemsLocalPaths = with(currentState) {
                    items.filter { it.id in command.itemIds }
                        .map { it.localImagePath }
                        .toSet()
                }

                viewModelScope.launch {
                    deleteItemsUseCase(
                        itemIds = command.itemIds.toList(),
                        itemLocalImagePaths = selectedItemsLocalPaths
                    )
                    selectedItemIds.value -= command.itemIds
                }
            }

            is ItemsCommand.ChangeSearchQuery -> {
                savedStateHandle[KEY_SEARCH_QUERY] = command.query
            }

            ItemsCommand.UnselectItems -> {
                selectedItemIds.value = emptySet()
            }

            ItemsCommand.CleanSearchQueryClick -> {
                savedStateHandle[KEY_SEARCH_QUERY] = ""
            }
        }
    }

    private fun changeSelectedStatus(itemId: Int) {
        val currentState = uiState.value as? ItemsUiState.Success ?: return

        if (itemId in currentState.selectedItemIds) {
            selectedItemIds.value -= itemId
        } else {
            selectedItemIds.value += itemId
        }
    }

    private fun navigateToSaveItem(itemId: Int) {
        viewModelScope.launch {
            _events.send(ItemsEvent.NavigateToSaveItem(itemId))
        }
    }
}

sealed interface ItemsCommand {
    data class DeleteItem(val itemId: Int, val itemLocalImagePath: String?) : ItemsCommand
    data class ClickItem(val itemId: Int) : ItemsCommand
    data class LongClickItem(val itemId: Int) : ItemsCommand
    data class DeleteItems(val itemIds: Set<Int>) : ItemsCommand
    data class ChangeSearchQuery(val query: String) : ItemsCommand
    data object CleanSearchQueryClick : ItemsCommand
    data object UnselectItems : ItemsCommand
}

sealed interface ItemsEvent {
    data class NavigateToSaveItem(val itemId: Int) : ItemsEvent
}

@Immutable
sealed interface ItemsUiState {
    data object Loading : ItemsUiState

    data class Success(
        val items: List<ItemUiModel>,
        val filteredItemsByQuery: List<ItemUiModel>,
        val selectedItemIds: Set<Int>,
        val searchQuery: String,
    ) : ItemsUiState
}

@Immutable
data class ItemUiModel(
    val id: Int,
    val name: String,
    val localImagePath: String?,
    val dateOfPurchase: LocalDate,
)