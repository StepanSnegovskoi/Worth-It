package com.metes.worthit.feature.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.core.designsystem.component.other.LoadingScreen
import com.metes.worthit.core.presentation.ObserveAsEvents
import com.metes.worthit.feature.settings.component.Items

@Composable
fun ItemsRoute(
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel(),
    onNavigateToSaveItem: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            is ItemsEvent.NavigateToSaveItem -> onNavigateToSaveItem(event.itemId)
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
        )
    }
}

@Composable
fun ItemsScreen(
    uiState: ItemsUiState.Success,
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onItemDeleteClick: (Int, String?) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current

    val combinedContentPadding = PaddingValues(
        start = scaffoldPadding.calculateStartPadding(layoutDirection) + 8.dp,
        top = scaffoldPadding.calculateTopPadding() + 8.dp,
        end = scaffoldPadding.calculateEndPadding(layoutDirection) + 8.dp,
        bottom = scaffoldPadding.calculateBottomPadding() + 8.dp
    )

    Items(
        items = uiState.uiItems,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        contentPadding = combinedContentPadding,
        onClick = onItemClick,
        onDeleteClick = onItemDeleteClick,
    )
}
