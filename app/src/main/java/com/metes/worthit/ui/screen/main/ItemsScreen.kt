package com.metes.worthit.ui.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.R
import com.metes.worthit.ui.component.LoadingScreen
import com.metes.worthit.ui.screen.main.component.Items
import kotlinx.coroutines.flow.StateFlow
import java.time.Clock

@Composable
fun ItemsRoute(
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel(),
    onNavigateToAddItem: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                ItemsEvent.NavigateToAddItem -> onNavigateToAddItem()
            }
        }
    }

    ItemsScreen(
        uiState = uiState,
        modifier = modifier,
        onAddItemClick = { viewModel.processCommand(ItemsCommand.AddItem) },
        onItemSwipe = { itemId: Int, itemLocalImagePath: String? ->
            viewModel.processCommand(ItemsCommand.DeleteItem(itemId, itemLocalImagePath))
        },
    )
}

@Composable
fun ItemsScreen(
    uiState: ItemsUiState,
    modifier: Modifier = Modifier,
    onAddItemClick: () -> Unit,
    onItemSwipe: (Int, String?) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            if (uiState is ItemsUiState.Success) {
                FloatingActionButton(
                    onClick = onAddItemClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add_24dp),
                        contentDescription = stringResource(R.string.add_item_desc)
                    )
                }
            }
        }
    ) { innerPadding ->
        when (uiState) {
            ItemsUiState.Loading -> {
                LoadingScreen(Modifier.padding(innerPadding))
            }

            is ItemsUiState.Success -> {
                Items(
                    items = uiState.uiItems,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    contentPadding = innerPadding,
                    onClick = {
                        TODO("to do ItemsScreen Items(onClick)")
                    },
                    onDismiss = onItemSwipe
                )
            }
        }
    }
}
