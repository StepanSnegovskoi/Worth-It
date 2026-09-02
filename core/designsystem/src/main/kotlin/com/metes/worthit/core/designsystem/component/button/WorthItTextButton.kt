package com.metes.worthit.core.designsystem.component.button

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.metes.worthit.core.designsystem.component.defaults.WorthItButtonDefaults
import com.metes.worthit.core.designsystem.component.preview.BackgroundForPreview
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItTextButton(
    text: String,
    modifier: Modifier = Modifier,
    colors: ButtonColors = WorthItButtonDefaults.colors(),
    shape: Shape = AppTheme.shape.button,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = colors,
        shape = shape,
    ) {
        WorthItText(text = text)
    }
}

@Preview
@Composable
fun WorthItTextButtonPreview() {
    BackgroundForPreview {
        WorthItTextButton(
            text = "Hello World",
            onClick = {}
        )
    }
}
