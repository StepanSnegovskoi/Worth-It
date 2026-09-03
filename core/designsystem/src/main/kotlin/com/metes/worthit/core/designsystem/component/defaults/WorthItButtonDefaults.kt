package com.metes.worthit.core.designsystem.component.defaults

import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import com.metes.worthit.core.designsystem.theme.AppTheme

object WorthItButtonDefaults {
    @Composable
    fun colors(): ButtonColors = ButtonColors(
        containerColor = AppTheme.colorScheme.primaryContainer,
        contentColor = AppTheme.colorScheme.onBackground,
        disabledContainerColor = AppTheme.colorScheme.surface,
        disabledContentColor = AppTheme.colorScheme.secondary
    )
}
