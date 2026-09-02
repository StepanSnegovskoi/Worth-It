package com.metes.worthit.core.designsystem.component.progress

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.preview.BackgroundForPreview
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.colorScheme.onBackground,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = color)
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    BackgroundForPreview(transparent = false) {
        LoadingScreen(modifier = Modifier.size(128.dp))
    }
}
