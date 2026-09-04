package com.metes.worthit.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.nav.WorthItTopAppBar
import com.metes.worthit.core.designsystem.component.preview.PreviewBottomTab
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.preview.WorthItScreenPreview
import com.metes.worthit.core.designsystem.component.progress.LoadingScreen
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.domain.entity.UserPreferences
import com.metes.worthit.core.presentation.toThemeColor
import com.metes.worthit.feature.settings.component.ThemeColors
import com.metes.worthit.feature.settings.component.ThemeModes
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (val currentUiState = uiState) {
        SettingsUiState.Loading -> LoadingScreen(modifier = modifier)

        is SettingsUiState.Success -> SettingsScreen(
            uiState = currentUiState,
            modifier = modifier,
            onSaveThemeColorClick = {
                viewModel.processCommand(SettingsCommand.SelectThemeColor(it))
            },
            onSaveThemeModeClick = {
                viewModel.processCommand(SettingsCommand.SelectThemeMode(it))
            },
            onBackClick = onBackClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    uiState: SettingsUiState.Success,
    modifier: Modifier = Modifier,
    onSaveThemeColorClick: (ThemeColor) -> Unit,
    onSaveThemeModeClick: (ThemeMode) -> Unit,
    onBackClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = AppTheme.colorScheme.background,
        topBar = {
            WorthItTopAppBar(
                title = {
                    WorthItText(text = stringResource(R.string.settings))
                },
                navigationIcon = {
                    WorthItIconButton(
                        onClick = {
                            keyboardController?.hide()
                            onBackClick()
                        },
                        content = {
                            WorthItIcon(drawableRes = DesignR.drawable.back_24dp, contentDescriptionRes = R.string.cd_back)
                        },
                    )
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 8.dp),
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

@Preview
@Composable
private fun SettingsScreenPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        WorthItScreenPreview(
            theme = theme,
            selectedTab = PreviewBottomTab.Settings
        ) { _, modifier ->
            SettingsScreen(
                uiState = SettingsUiState.Success(
                    preferences = UserPreferences(
                        currency = Currency.EUR,
                        themeColor = theme.color.toThemeColor,
                        themeMode = ThemeMode.DARK
                    )
                ),
                modifier = modifier,
                onSaveThemeColorClick = { },
                onSaveThemeModeClick = { },
                onBackClick = { },
            )
        }
    }
}
