package com.metes.worthit.feature.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.core.designsystem.component.animation.WorthItAnimatedVisibility
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.preview.PreviewBottomTab
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.preview.WorthItScreenPreview
import com.metes.worthit.core.designsystem.component.progress.LoadingScreen
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.presentation.ObserveAsEvents
import com.metes.worthit.feature.items.component.Items
import com.metes.worthit.feature.items.component.ItemsListIsEmpty
import java.time.LocalDate
import com.metes.worthit.core.designsystem.R as DesignR

private val bottomButtonSize = 48.dp
private val bottomButtonSpaceAround = 16.dp
private val scrollableBottomButtonClearance = bottomButtonSize + bottomButtonSpaceAround * 2

@Composable
fun ItemsRoute(
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel(),
    onNavigateToEditingItem: (Int) -> Unit,
    onNavigateToAddingItem: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ItemsEvent.NavigateToSaveItem -> onNavigateToEditingItem(event.itemId)
        }
    }

    when (val currentState = uiState) {
        ItemsUiState.Loading -> LoadingScreen(modifier = modifier)

        is ItemsUiState.Success -> ItemsScreen(
            uiState = currentState,
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
        )
    }
}

@Composable
fun ItemsScreen(
    uiState: ItemsUiState.Success,
    modifier: Modifier = Modifier,
    onItemDeleteClick: (Int, String?) -> Unit,
    onItemClick: (Int) -> Unit,
    onItemLongClick: (Int) -> Unit,
    onItemsDeleteClick: (Set<Int>) -> Unit,
    onEmptyListClick: () -> Unit,
    onUnselectItemsClick: () -> Unit,
) {
    val statusBarsPaddingDp = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background),
    ) {
        Items(
            items = uiState.items,
            selectedItemIds = uiState.selectedItemIds,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp)
                .navigationBarsPadding(),
            contentPadding = PaddingValues(
                top = statusBarsPaddingDp + 8.dp,
                bottom = scrollableBottomButtonClearance
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
                    ItemsListIsEmpty(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = onEmptyListClick,
                    )
                }
            }
        )

        ItemsFab(
            visible = uiState.selectedItemIds.isNotEmpty(),
            selectedItemIds = uiState.selectedItemIds,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
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

@Composable
private fun ItemsFab(
    visible: Boolean,
    selectedItemIds: Set<Int>,
    modifier: Modifier = Modifier,
    onItemsDeleteClick: (Set<Int>) -> Unit,
    onUnselectItemsClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        WorthItAnimatedVisibility(visible = visible) {
            WorthItIconButton(
                modifier = Modifier.size(bottomButtonSize),
                onClick = {
                    onItemsDeleteClick(selectedItemIds)
                }
            ) {
                WorthItIcon(
                    drawableRes = DesignR.drawable.delete_48dp,
                    contentDescriptionRes = R.string.cd_delete_selected_items,
                    tint = AppTheme.colorScheme.primary,
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        WorthItAnimatedVisibility(visible = visible) {
            WorthItIconButton(
                modifier = Modifier.size(bottomButtonSize),
                onClick = {
                    onUnselectItemsClick()
                }
            ) {
                WorthItIcon(
                    drawableRes = R.drawable.hand_off_48dp,
                    contentDescriptionRes = R.string.cd_unselect_items,
                    tint = AppTheme.colorScheme.primary,
                )
            }
        }
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
                selectedItemIds = setOf(2, 4)
            ),
            modifier = modifier,
            onItemDeleteClick = { _, _ -> },
            onItemClick = { },
            onItemLongClick = { },
            onItemsDeleteClick = { },
            onEmptyListClick = { },
            onUnselectItemsClick = { },
        )
    }
}
