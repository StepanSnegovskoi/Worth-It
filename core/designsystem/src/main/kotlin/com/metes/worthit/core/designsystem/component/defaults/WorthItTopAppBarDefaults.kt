package com.metes.worthit.core.designsystem.component.defaults

import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.metes.worthit.core.designsystem.theme.AppTheme

object WorthItTopAppBarDefaults {
    @Composable
    fun colors(
        containerColor: Color = AppTheme.colorScheme.background,
        scrolledContainerColor: Color = AppTheme.colorScheme.background,
        navigationIconContentColor: Color = AppTheme.colorScheme.primary,
        titleContentColor: Color = AppTheme.colorScheme.onBackground,
        actionIconContentColor: Color = AppTheme.colorScheme.primary,
        subtitleContentColor: Color = AppTheme.colorScheme.primary,
    ): TopAppBarColors = TopAppBarColors(
        containerColor = containerColor,
        scrolledContainerColor = scrolledContainerColor,
        navigationIconContentColor = navigationIconContentColor,
        titleContentColor = titleContentColor,
        actionIconContentColor = actionIconContentColor,
        subtitleContentColor = subtitleContentColor,
    )
}