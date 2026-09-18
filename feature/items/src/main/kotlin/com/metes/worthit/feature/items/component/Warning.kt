package com.metes.worthit.feature.items.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.surface.ContentWrapper
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.items.R

@Composable
internal fun Warning(
    text: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    @DrawableRes iconRes: Int? = null,
    onClick: (() -> Unit)? = null,
) {
    ContentWrapper(
        modifier = modifier,
        onClick = onClick,
        alpha = AppTheme.alpha.low,
        color = AppTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            WorthItText(
                text = text,
                maxLines = Int.MAX_VALUE,
                color = AppTheme.colorScheme.onSurface,
            )
            if (iconRes != null) {
                WorthItIcon(drawableRes = iconRes, tint = AppTheme.colorScheme.primary)
            }
            if (actionText != null) {
                WorthItText(
                    text = actionText,
                    maxLines = Int.MAX_VALUE,
                    color = AppTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Preview
@Composable
private fun WarningPreviewAll(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Box(
            modifier = Modifier
                .background(AppTheme.colorScheme.background)
                .width(240.dp)
                .height(320.dp),
            contentAlignment = Alignment.Center
        ) {
            Warning(
                text = stringResource(R.string.the_list_of_items_is_empty),
                actionText = stringResource(R.string.let_s_add_something),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                iconRes = R.drawable.wind_40dp,
                onClick = { }
            )
        }
    }
}

@Preview
@Composable
private fun WarningPreviewText(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Box(
            modifier = Modifier
                .background(AppTheme.colorScheme.background)
                .width(240.dp)
                .height(320.dp),
            contentAlignment = Alignment.Center
        ) {
            Warning(
                text = stringResource(R.string.the_list_of_items_is_empty),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { }
            )
        }
    }
}
