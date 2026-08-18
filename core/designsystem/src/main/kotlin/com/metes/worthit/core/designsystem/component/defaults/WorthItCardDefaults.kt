package com.metes.worthit.core.designsystem.component.defaults

import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.metes.worthit.core.designsystem.theme.AppTheme

object WorthItCardDefaults {
    @Composable
    fun colors(
        containerColor: Color = AppTheme.colorScheme.surface,
        contentColor: Color = AppTheme.colorScheme.onSurface,
        disabledContainerColor: Color = AppTheme.colorScheme.secondary,
        disabledContentColor: Color = AppTheme.colorScheme.onSurface,
    ): CardColors = CardColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )
}
