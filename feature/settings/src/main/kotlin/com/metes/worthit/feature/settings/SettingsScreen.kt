package com.metes.worthit.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.core.designsystem.component.other.LoadingScreen
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.feature.settings.component.ThemeColors
import com.metes.worthit.feature.settings.component.ThemeModes

@Composable
fun SettingsRoute(
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val currentUiState = uiState) {
        SettingsUiState.Loading -> LoadingScreen(modifier = modifier.padding(scaffoldPadding))

        is SettingsUiState.Success -> SettingsScreen(
            uiState = currentUiState,
            scaffoldPadding = scaffoldPadding,
            modifier = modifier,
            onSaveThemeColorClick = {
                viewModel.processCommand(SettingsCommand.SelectThemeColor(it))
            },
            onSaveThemeModeClick = {
                viewModel.processCommand(SettingsCommand.SelectThemeMode(it))
            },
        )
    }

}

@Composable
fun SettingsScreen(
    uiState: SettingsUiState.Success,
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onSaveThemeColorClick: (ThemeColor) -> Unit,
    onSaveThemeModeClick: (ThemeMode) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
            .padding(scaffoldPadding)
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ThemeColors(
                selectedThemeColor = uiState.preferences.themeColor,
                isDarkTheme = AppTheme.isDarkTheme,
                onClick = {
                    onSaveThemeColorClick(it)
                }
            )
            ThemeModes(
                selectedThemeMode = uiState.preferences.themeMode,
                onClick = {
                    onSaveThemeModeClick(it)
                }
            )
        }
    }
}
