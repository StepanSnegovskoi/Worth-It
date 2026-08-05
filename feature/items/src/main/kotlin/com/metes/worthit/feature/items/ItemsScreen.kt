package com.metes.worthit.feature.items

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
            modifier = modifier,
            onItemSwipe = { itemId: Int, itemLocalImagePath: String? ->
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
    modifier: Modifier = Modifier,
    onItemSwipe: (Int, String?) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
    ) { innerPadding ->
        Items(
            items = uiState.uiItems,
            currentDate = uiState.currentDate,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            contentPadding = innerPadding,
            onClick = onItemClick,
            onDismiss = onItemSwipe,
        )
    }
}
