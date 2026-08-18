package com.metes.worthit.core.designsystem.component.defaults

import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.metes.worthit.core.designsystem.theme.AppTheme

object WorthItIconButtonDefaults {
    @Composable
    fun colors(
        contentColor: Color = AppTheme.colorScheme.primary,
        disabledContentColor: Color = Color.Unspecified,
        containerColor: Color = Color.Transparent,
        disabledContainerColor: Color = Color.Transparent,
    ): IconButtonColors = IconButtonColors(
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        containerColor = containerColor,
        disabledContainerColor = disabledContainerColor,
    )
}
