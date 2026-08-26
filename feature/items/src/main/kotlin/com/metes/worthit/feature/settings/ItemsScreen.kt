package com.metes.worthit.feature.settings

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.core.designsystem.component.defaults.WorthItFloatingActionButtonDefaults
import com.metes.worthit.core.designsystem.component.other.LoadingScreen
import com.metes.worthit.core.designsystem.component.other.WorthItAnimatedVisibility
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.presentation.ObserveAsEvents
import com.metes.worthit.feature.items.R
import com.metes.worthit.feature.settings.component.Items

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
        when(event) {
            is ItemsEvent.NavigateToSaveItem -> onNavigateToEditingItem(event.itemId)
        }
    }

    when (val currentState = uiState) {
        ItemsUiState.Loading -> LoadingScreen(
            modifier = modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        )

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
            }
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
) {
    val layoutDirection = LocalLayoutDirection.current

    val combinedContentPadding = PaddingValues(
        start = scaffoldPadding.calculateStartPadding(layoutDirection) + 16.dp,
        top = scaffoldPadding.calculateTopPadding() + 8.dp,
        end = scaffoldPadding.calculateEndPadding(layoutDirection) + 16.dp,
        bottom = scaffoldPadding.calculateBottomPadding() + 16.dp - WorthItFloatingActionButtonDefaults.fabHeight
    )

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            WorthItAnimatedVisibility(visible = uiState.selectedItemIds.isNotEmpty()) {
                FloatingActionButton(
                    containerColor = AppTheme.colorScheme.primary,
                    onClick = {
                        onItemsDeleteClick(uiState.selectedItemIds)
                    }
                ) {
                    WorthItIcon(
                        drawableRes = R.drawable.delete_32dp,
                        contentDescriptionRes = R.string.cd_delete_selected_items,
                        tint = AppTheme.colorScheme.onPrimary,
                    )
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
