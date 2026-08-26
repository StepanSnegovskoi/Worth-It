package com.metes.worthit.feature.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.core.designsystem.component.other.LoadingScreen
import com.metes.worthit.core.designsystem.component.other.WorthItAnimatedVisibility
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItIconButton
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.presentation.ObserveAsEvents
import com.metes.worthit.feature.items.R
import com.metes.worthit.feature.settings.component.Items

private val bottomButtonSize = 48.dp

@Composable
fun ItemsRoute(
    scaffoldPadding: PaddingValues,
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
            scaffoldPadding = scaffoldPadding,
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ItemsScreen(
    uiState: ItemsUiState.Success,
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onItemDeleteClick: (Int, String?) -> Unit,
    onItemClick: (Int) -> Unit,
    onItemLongClick: (Int) -> Unit,
    onItemsDeleteClick: (Set<Int>) -> Unit,
    onEmptyListClick: () -> Unit,
    onUnselectItemsClick: () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current

    val combinedContentPadding = PaddingValues(
        start = scaffoldPadding.calculateStartPadding(layoutDirection) + 16.dp,
        top = scaffoldPadding.calculateTopPadding() + 8.dp,
        end = scaffoldPadding.calculateEndPadding(layoutDirection) + 16.dp,
        bottom = bottomButtonSize + 16.dp * 2
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                WorthItAnimatedVisibility(visible = uiState.selectedItemIds.isNotEmpty()) {
                    WorthItIconButton(
                        modifier = Modifier.size(bottomButtonSize),
                        onClick = {
                            onItemsDeleteClick(uiState.selectedItemIds)
                        }
                    ) {
                        WorthItIcon(
                            drawableRes = R.drawable.delete_48dp,
                            contentDescriptionRes = R.string.cd_delete_selected_items,
                            tint = AppTheme.colorScheme.primary,
                        )
                    }
                }
                Spacer(Modifier.width(8.dp))
                WorthItAnimatedVisibility(visible = uiState.selectedItemIds.isNotEmpty()) {
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
        },
        containerColor = AppTheme.colorScheme.background
    ) {
        Items(
            items = uiState.items,
            selectedItemIds = uiState.selectedItemIds,
            contentPadding = combinedContentPadding,
            modifier = Modifier
                .fillMaxSize(),
            onClick = onItemClick,
            onLongClick = onItemLongClick,
            onEmptyListClick = onEmptyListClick,
            onDeleteClick = onItemDeleteClick,
        )
    }
}
