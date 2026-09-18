package com.metes.worthit.feature.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.core.designsystem.component.preview.PreviewBottomTab
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.preview.WorthItScreenPreview
import com.metes.worthit.core.designsystem.component.progress.LoadingScreen
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.presentation.ObserveAsEvents
import com.metes.worthit.feature.items.component.Items
import com.metes.worthit.feature.items.component.ItemsFloatingActionButton
import com.metes.worthit.feature.items.component.SearchBar
import com.metes.worthit.feature.items.component.Warning
import com.metes.worthit.feature.items.component.bottomButtonSpaceAround
import com.metes.worthit.feature.items.component.scrollableBottomButtonClearance
import java.time.LocalDate


@Composable
fun ItemsRoute(
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel(),
    onNavigateToEditingItem: (Int) -> Unit,
    onNavigateToAddingItem: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ItemsEvent.NavigateToSaveItem -> onNavigateToEditingItem(event.itemId)
        }
    }

    when (val currentState = uiState) {
        ItemsUiState.Loading -> LoadingScreen(modifier = modifier)

        is ItemsUiState.Success -> ItemsScreen(
            uiState = currentState,
            focusManager = focusManager,
            modifier = modifier,
            onItemDeleteClick = { itemId: Int, itemLocalImagePath: String? ->
                viewModel.processCommand(ItemsCommand.DeleteItem(itemId, itemLocalImagePath))
            },
            onItemClick = { itemId: Int ->
                viewModel.processCommand(ItemsCommand.ClickItem(itemId))
            },
            onItemLongClick = { itemId: Int ->
                viewModel.processCommand(ItemsCommand.LongClickItem(itemId))
            },
            onEmptyListClick = onNavigateToAddingItem,
            onItemsDeleteClick = {
                viewModel.processCommand(ItemsCommand.DeleteItems(it))
            },
            onUnselectItemsClick = {
                viewModel.processCommand(ItemsCommand.UnselectItems)
            },
            onQueryChanged = {
                viewModel.processCommand(ItemsCommand.ChangeSearchQuery(it))
            },
            onCleanClickSearchQuery = {
                viewModel.processCommand(ItemsCommand.CleanSearchQueryClick)
            }
        )
    }
}

@Composable
internal fun ItemsScreen(
    uiState: ItemsUiState.Success,
    focusManager: FocusManager,
    modifier: Modifier = Modifier,
    onItemDeleteClick: (Int, String?) -> Unit,
    onItemClick: (Int) -> Unit,
    onItemLongClick: (Int) -> Unit,
    onItemsDeleteClick: (Set<Int>) -> Unit,
    onEmptyListClick: () -> Unit,
    onUnselectItemsClick: () -> Unit,
    onQueryChanged: (String) -> Unit,
    onCleanClickSearchQuery: () -> Unit,
) {
    val itemsState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = {
                    focusManager.clearFocus()
                },
            )
            .systemBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .imePadding()
                .padding(horizontal = 8.dp)
        ) {
            SearchBar(
                value = uiState.searchQuery,
                onValueChange = onQueryChanged,
                onCleanClick = onCleanClickSearchQuery
            )

            Items(
                state = itemsState,
                items = uiState.filteredItemsByQuery,
                selectedItemIds = uiState.selectedItemIds,
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    bottom = scrollableBottomButtonClearance,
                    top = 8.dp,
                ),
                onClick = onItemClick,
                onLongClick = onItemLongClick,
                onDeleteClick = onItemDeleteClick,
                contentIfEmpty = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .clip(AppTheme.shape.container),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (uiState.items.isEmpty()) {
                            Warning(
                                text = stringResource(R.string.the_list_of_items_is_empty),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                actionText = stringResource(R.string.let_s_add_something),
                                iconRes = R.drawable.wind_40dp,
                                onClick = onEmptyListClick,
                            )
                        } else {
                            Warning(
                                text = stringResource(R.string.nothing_was_founded),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                iconRes = R.drawable.wind_40dp,
                            )
                        }
                    }
                }
            )
        }

        ItemsFloatingActionButton(
            visible = uiState.selectedItemIds.isNotEmpty(),
            selectedItemIds = uiState.selectedItemIds,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .imePadding()
                .navigationBarsPadding()
                .padding(
                    bottom = bottomButtonSpaceAround,
                    end = bottomButtonSpaceAround,
                ),
            onItemsDeleteClick = onItemsDeleteClick,
            onUnselectItemsClick = onUnselectItemsClick,
        )
    }
}

@Preview
@Composable
private fun ItemsScreenPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    WorthItScreenPreview(
        theme = theme,
        selectedTab = PreviewBottomTab.Items
    ) { _, modifier ->
        ItemsScreen(
            uiState = ItemsUiState.Success(
                items = buildList {
                    repeat(5) {
                        val item = ItemUiModel(
                            id = it,
                            name = "Car",
                            localImagePath = null,
                            dateOfPurchase = LocalDate.now(),
                        )
                        add(item)
                    }
                },
                selectedItemIds = setOf(2, 4),
                searchQuery = "",
                filteredItemsByQuery = buildList {
                    repeat(5) {
                        val item = ItemUiModel(
                            id = it,
                            name = "Car",
                            localImagePath = null,
                            dateOfPurchase = LocalDate.now(),
                        )
                        add(item)
                    }
                }
            ),
            focusManager = LocalFocusManager.current,
            modifier = modifier,
            onItemDeleteClick = { _, _ -> },
            onItemClick = { },
            onItemLongClick = { },
            onItemsDeleteClick = { },
            onEmptyListClick = { },
            onUnselectItemsClick = { },
            onQueryChanged = { },
            onCleanClickSearchQuery = { },
        )
    }
}
