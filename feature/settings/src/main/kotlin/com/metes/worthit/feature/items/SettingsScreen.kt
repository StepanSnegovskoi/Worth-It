package com.metes.worthit.feature.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.core.designsystem.component.other.LoadingScreen
import com.metes.worthit.core.designsystem.component.other.WorthItText

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    scaffoldPadding: PaddingValues,
    viewModel: SettingsViewModel = hiltViewModel(),
) {

    SettingsScreen(modifier = modifier, scaffoldPadding = scaffoldPadding)
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    scaffoldPadding: PaddingValues,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(scaffoldPadding),
        contentAlignment = Alignment.Center,
    ) {
        WorthItText(text = "Settings Screen")
    }
}
