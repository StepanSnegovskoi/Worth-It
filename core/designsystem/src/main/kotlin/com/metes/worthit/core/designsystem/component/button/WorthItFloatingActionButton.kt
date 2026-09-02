package com.metes.worthit.core.designsystem.component.button

import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.preview.BackgroundForPreview
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.designsystem.theme.LocalAppTheme

@Composable
fun WorthItFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = AppTheme.colorScheme.primary,
    content: @Composable () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        containerColor = containerColor,
        onClick = onClick,
        content = content,
    )
}

@Preview
@Composable
fun WorthItFloatingActionButtonPreview() {
    BackgroundForPreview {
        WorthItFloatingActionButton(
            onClick = {},
            content = {
                WorthItIcon(
                    drawableRes = R.drawable.add_24dp,
                    tint = LocalAppTheme.current.onPrimary
                )
            }
        )
    }
}
