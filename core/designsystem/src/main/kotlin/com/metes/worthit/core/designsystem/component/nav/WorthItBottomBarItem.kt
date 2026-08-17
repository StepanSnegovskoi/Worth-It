package com.metes.worthit.core.designsystem.component.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun RowScope.WorthItBottomBarItem(
    selected: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    @StringRes titleRes: Int,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        modifier = modifier,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = contentDescription
            )
        },
        colors = NavigationBarItemColors(
            selectedIconColor = AppTheme.colorScheme.primary,
            selectedTextColor = AppTheme.colorScheme.primary,
            unselectedIconColor = AppTheme.colorScheme.secondary,
            unselectedTextColor = AppTheme.colorScheme.secondary,
            selectedIndicatorColor = Color.Transparent,
            disabledIconColor = Color.Unspecified,
            disabledTextColor = Color.Unspecified,
        ),
        label = { WorthItText(stringResource(titleRes)) }
    )
}
