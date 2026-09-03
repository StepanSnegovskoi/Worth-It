package com.metes.worthit.core.designsystem.component.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.defaults.WorthItNavigationBarDefaults
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun RowScope.WorthItBottomBarItem(
    selected: Boolean,
    modifier: Modifier = Modifier,
    @StringRes contentDescriptionRes: Int? = null,
    title: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        modifier = modifier,
        onClick = onClick,
        icon = {
            WorthItIcon(
                drawableRes = iconRes,
                contentDescriptionRes = contentDescriptionRes,
            )
        },
        colors = WorthItNavigationBarDefaults.colors(),
        label = { WorthItText(title) },
    )
}

@Preview
@Composable
fun WorthItBottomBarItemPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            Row {
                WorthItBottomBarItem(
                    selected = true,
                    onClick = {},
                    title = "Items",
                    iconRes = R.drawable.items_24dp
                )
                WorthItBottomBarItem(
                    selected = true,
                    onClick = {},
                    title = "Process Item",
                    iconRes = R.drawable.edit_24dp
                )
                WorthItBottomBarItem(
                    selected = true,
                    onClick = {},
                    title = "Settings",
                    iconRes = R.drawable.settings_24dp
                )
            }
        }
    }
}
