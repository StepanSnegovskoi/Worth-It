package com.metes.worthit.core.designsystem.component.defaults

import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.metes.worthit.core.designsystem.theme.AppTheme

data object WorthItNavigationBarDefaults {
    @Composable
    fun colors(
        selectedIconColor: Color = AppTheme.colorScheme.primary,
        selectedTextColor: Color = AppTheme.colorScheme.primary,
        unselectedIconColor: Color = AppTheme.colorScheme.secondary,
        unselectedTextColor: Color = AppTheme.colorScheme.secondary,
        selectedIndicatorColor: Color = Color.Transparent,
        disabledIconColor: Color = AppTheme.colorScheme.secondary,
        disabledTextColor: Color = AppTheme.colorScheme.secondary,
    ): NavigationBarItemColors = NavigationBarItemColors(
        selectedIconColor = selectedIconColor,
        selectedTextColor = selectedTextColor,
        unselectedIconColor = unselectedIconColor,
        unselectedTextColor = unselectedTextColor,
        selectedIndicatorColor = selectedIndicatorColor,
        disabledIconColor = disabledIconColor,
        disabledTextColor = disabledTextColor,
    )
}
