package com.metes.worthit.core.designsystem.component.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.theme.LocalAppTheme

@Preview
@Composable
fun WorthItBackgroundPreview() {
    BackgroundForPreview {
        Box(
            modifier = Modifier
                .size(128.dp)
                .background(LocalAppTheme.current.background)
        )
    }
}
