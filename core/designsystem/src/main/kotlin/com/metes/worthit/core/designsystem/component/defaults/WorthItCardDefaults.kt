package com.metes.worthit.core.designsystem.component.defaults

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

    val shape: Shape = RoundedCornerShape(16.dp)

    val elevation: Dp = 8.dp
}
