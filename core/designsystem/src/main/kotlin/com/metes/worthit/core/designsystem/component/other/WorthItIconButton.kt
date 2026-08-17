package com.metes.worthit.core.designsystem.component.other

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItIconButton(
    modifier: Modifier = Modifier,
    shape: Shape = AppTheme.shape.button,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        colors = IconButtonColors(
            contentColor = AppTheme.colorScheme.primary,
            disabledContentColor = Color.Unspecified,
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        )
    ) {
        content()
    }
}
