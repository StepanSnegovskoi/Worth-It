package com.metes.worthit.feature.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.metes.worthit.core.designsystem.component.other.ContentWrapper
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.extensions.primaryColor
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.presentation.nameStringRes
import com.metes.worthit.core.presentation.toPrimaryThemeColor
import com.metes.worthit.feature.settings.R

@Composable
fun ThemeColors(
    selectedThemeColor: ThemeColor,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    textColor: Color = AppTheme.colorScheme.onBackground,
    themeColors: List<ThemeColor> = ThemeColor.entries,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    innerPadding: Dp = 8.dp,
    onClick: (ThemeColor) -> Unit,
) {
    WorthItText(
        text = stringResource(R.string.color),
        modifier = Modifier.padding(start = innerPadding),
        color = AppTheme.colorScheme.onBackground,
    )
    ContentWrapper(
        modifier = Modifier.fillMaxWidth(),
        color = AppTheme.colorScheme.surface,
        alpha = 1f,
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
        ) {
            themeColors.fastForEach { themeColor ->
                val primaryLightColor = themeColor.toPrimaryThemeColor().primaryColor(false)
                val primaryDarkColor = themeColor.toPrimaryThemeColor().primaryColor(true)

                OneSettingItem(
                    text = stringResource(themeColor.nameStringRes),
                    selected = themeColor == selectedThemeColor,
                    brush = Brush.linearGradient(
                        0.0f to if (isDarkTheme) primaryLightColor else primaryDarkColor,
                        0.5f to if (isDarkTheme) primaryLightColor else primaryDarkColor,
                        0.5f to if (isDarkTheme) primaryDarkColor else primaryLightColor,
                        1f to if (isDarkTheme) primaryDarkColor else primaryLightColor,
                    ),
                    innerPadding = innerPadding,
                    modifier = Modifier.fillMaxWidth(),
                    textColor = textColor,
                    borderColor = if (isDarkTheme) primaryLightColor else primaryDarkColor,
                    onClick = {
                        onClick(themeColor)
                    },
                )
            }
        }
    }
}
