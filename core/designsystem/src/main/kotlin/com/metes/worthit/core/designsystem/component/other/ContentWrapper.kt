package com.metes.worthit.core.designsystem.component.other

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.metes.worthit.core.designsystem.extensions.clickableIfNotNull
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun ContentWrapper(
    modifier: Modifier = Modifier,
    shape: Shape = AppTheme.shape.container,
    color: Color = AppTheme.colorScheme.primary,
    alpha: Float = AppTheme.alpha.extraLow,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier
            .clip(shape)
            .clickableIfNotNull(onClick = onClick),
        shape = shape,
        color = color.copy(alpha = alpha),
        content = content,
    )
}
