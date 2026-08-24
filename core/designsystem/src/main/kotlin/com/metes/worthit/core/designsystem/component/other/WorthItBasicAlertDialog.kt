package com.metes.worthit.core.designsystem.component.other

import androidx.compose.foundation.background
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.metes.worthit.core.designsystem.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorthItBasicAlertDialog(
    visible: Boolean,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    if (visible) {
        BasicAlertDialog(
            modifier = modifier
                .clip(AppTheme.shape.container)
                .background(AppTheme.colorScheme.surface),
            onDismissRequest = onDismissRequest,
            content = content,
        )
    }
}
