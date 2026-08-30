package com.metes.worthit.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.metes.worthit.core.designsystem.component.defaults.WorthItCardDefaults
import com.metes.worthit.core.designsystem.component.other.WorthItCard
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.component.other.WorthItTextButton
import com.metes.worthit.core.designsystem.extensions.primaryColor
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.designsystem.theme.LocalIsDarkTheme
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.presentation.toPrimaryThemeColor

@Composable
fun SettingsRoute(
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {

    SettingsScreen(
        scaffoldPadding = scaffoldPadding,
        modifier = modifier,
        onSaveThemeColorClick = {
            viewModel.processCommand(SettingsCommand.SelectThemeColor(it))
        },
    )
}

@Composable
fun SettingsScreen(
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onSaveThemeColorClick: (ThemeColor) -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WorthItText(text = "Settings Screen", color = AppTheme.colorScheme.onBackground)
            WorthItText(text = "Theme", color = AppTheme.colorScheme.onBackground)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ThemeColor.entries.fastForEach {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Brush.linearGradient(
                                    0.0f to it.toPrimaryThemeColor().primaryColor(true),
                                    0.5f to it.toPrimaryThemeColor().primaryColor(true),
                                    0.5f to it.toPrimaryThemeColor().primaryColor(false),
                                    1f to it.toPrimaryThemeColor().primaryColor(false),
                                )
                            )
                            .clickable {
                                onSaveThemeColorClick(it)
                            }
                    )
                }
            }
        }
    }
}
