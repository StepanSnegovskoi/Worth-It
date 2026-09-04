package com.metes.worthit.feature.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.extensions.primaryColor
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.presentation.nameStringRes
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
fun OneSettingItem(
    text: String,
    selected: Boolean,
    brush: Brush,
    innerPadding: Dp,
    modifier: Modifier = Modifier,
    textColor: Color = AppTheme.colorScheme.onBackground,
    shape: Shape = CircleShape,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    borderColor: Color = Color.Transparent,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(innerPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(shape)
                .border(width = 1.dp, shape = shape, color = borderColor)
                .background(brush),
        )
        WorthItText(text = text, color = textColor)
        Spacer(Modifier.weight(1f))
        if (selected) {
            WorthItIcon(
                drawableRes = DesignR.drawable.done_24dp,
                tint = AppTheme.colorScheme.correct,
            )
        }
    }
}

@Preview
@Composable
private fun OneSettingItemPreviewThemeModes(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.surface) {
            Column {
                ThemeMode.entries.forEachIndexed { index, themeMode ->
                    OneSettingItem(
                        text = stringResource(themeMode.nameStringRes),
                        selected = index % 2 == 0,
                        brush = Brush.linearGradient(
                            0.0f to Color.White,
                            0.5f to Color.White,
                            0.5f to Color.Black,
                            1f to Color.Black,
                        ),
                        innerPadding = 8.dp,
                        onClick = { },
                        borderColor = if (theme.isDark) Color.White else Color.Black,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OneSettingItemPreviewThemeColors(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    val primaryColorLight = theme.color.primaryColor(!theme.isDark)
    val primaryColorDark = theme.color.primaryColor(theme.isDark)

    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.surface) {
            OneSettingItem(
                text = stringResource(theme.color.nameStringRes),
                selected = true,
                brush = Brush.linearGradient(
                    0.0f to primaryColorLight,
                    0.5f to primaryColorLight,
                    0.5f to primaryColorDark,
                    1f to primaryColorDark,
                ),
                innerPadding = 8.dp,
                onClick = { },
                borderColor = if (theme.isDark) primaryColorLight else primaryColorDark,
            )
        }
    }
}
