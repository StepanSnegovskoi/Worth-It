package com.metes.worthit.ui.screen.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ItemsRoute(
    modifier: Modifier = Modifier,
    viewModel: ItemsViewModel = hiltViewModel()
) {
    ItemsScreen(modifier = modifier)
}

@Composable
fun ItemsScreen(
    modifier: Modifier = Modifier
) {
    Text(text = "ItemsScreen")
}