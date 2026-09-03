package com.metes.worthit.core.designsystem.component.surface

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.text.WorthItText
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

@Preview
@Composable
fun ContentWrapperPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        ContentWrapper {
            WorthItText(text = "Hello World", modifier = Modifier.padding(8.dp))
        }
    }
}
