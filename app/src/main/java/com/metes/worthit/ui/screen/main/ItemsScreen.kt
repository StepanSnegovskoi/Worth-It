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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.R
import com.metes.worthit.ui.component.LoadingScreen
import com.metes.worthit.ui.screen.main.component.Items

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
    )
}

@Composable
fun ItemsScreen(
    uiState: ItemsUiState,
    modifier: Modifier = Modifier,
    onAddItemClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
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
                    items = uiState.items,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    }
}