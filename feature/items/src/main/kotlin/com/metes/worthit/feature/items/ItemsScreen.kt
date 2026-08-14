package com.metes.worthit.feature.items

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.core.designsystem.component.other.LoadingScreen
import com.metes.worthit.feature.items.component.Items

@Composable
fun ItemsRoute(
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel(),
    onNavigateToSaveItem: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when(it) {
                is ItemsEvent.NavigateToSaveItem -> onNavigateToSaveItem(it.itemId)
            }
        }
    }

    when (val currentState = uiState) {
        ItemsUiState.Loading -> LoadingScreen()
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
    Items(
        items = uiState.uiItems,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = scaffoldPadding,
        onClick = onItemClick,
        onDeleteClick = onItemDeleteClick,
    )
}
