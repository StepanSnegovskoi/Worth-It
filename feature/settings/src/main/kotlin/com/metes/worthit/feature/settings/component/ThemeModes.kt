package com.metes.worthit.feature.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.metes.worthit.core.designsystem.component.other.ContentWrapper
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.presentation.linearBrushGradient
import com.metes.worthit.core.presentation.nameStringRes
import com.metes.worthit.feature.settings.R

@Composable
fun ThemeModes(
    selectedThemeMode: ThemeMode,
    modifier: Modifier = Modifier.Companion,
    textColor: Color = AppTheme.colorScheme.onBackground,
    themeModes: List<ThemeMode> = ThemeMode.entries,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    innerPadding: Dp = 8.dp,
    onClick: (ThemeMode) -> Unit,
) {
    WorthItText(
        text = stringResource(R.string.theme),
        modifier = Modifier.padding(start = innerPadding),
        color = AppTheme.colorScheme.onBackground,
    )
    ContentWrapper(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
        ) {
            themeModes.fastForEach { themeMode ->
                OneSettingItem(
                    text = stringResource(themeMode.nameStringRes),
                    selected = themeMode == selectedThemeMode,
                    brush = themeMode.linearBrushGradient,
                    innerPadding = innerPadding,
                    modifier = Modifier.fillMaxWidth(),
                    textColor = textColor,
                    onClick = {
                        onClick(themeMode)
                    },
                )
            }
        }
    }
}
