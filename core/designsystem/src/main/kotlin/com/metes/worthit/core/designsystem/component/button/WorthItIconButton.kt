package com.metes.worthit.core.designsystem.component.button

import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.defaults.WorthItIconButtonDefaults
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = AppTheme.shape.button,
    colors: IconButtonColors = WorthItIconButtonDefaults.colors(),
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        colors = colors,
        enabled = enabled,
    ) {
        content()
    }
}

@Preview
@Composable
fun WorthItIconButtonPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        WorthItIconButton(
            onClick = {},
            content = {
                WorthItIcon(
                    drawableRes = R.drawable.edit_24dp
                )
            }
        )
    }
}
