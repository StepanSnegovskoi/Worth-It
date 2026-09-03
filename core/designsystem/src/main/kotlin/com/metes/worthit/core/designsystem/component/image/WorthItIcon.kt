package com.metes.worthit.core.designsystem.component.image

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItIcon(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier,
    @StringRes contentDescriptionRes: Int? = null,
    tint: Color = LocalContentColor.current,
) {
    Icon(
        modifier = modifier,
        tint = tint,
        painter = painterResource(drawableRes),
        contentDescription = contentDescriptionRes?.let {
            stringResource(contentDescriptionRes)
        }
    )
}

@Preview
@Composable
fun WorthItIconPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                WorthItIcon(
                    drawableRes = R.drawable.edit_24dp
                )
                WorthItIcon(
                    drawableRes = R.drawable.edit_24dp,
                    tint = AppTheme.colorScheme.primary,
                )
                WorthItIcon(
                    drawableRes = R.drawable.edit_24dp,
                    tint = AppTheme.colorScheme.secondary,
                )
            }
        }
    }
}
