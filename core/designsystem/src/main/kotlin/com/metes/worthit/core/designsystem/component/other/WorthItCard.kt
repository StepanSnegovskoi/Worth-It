package com.metes.worthit.core.designsystem.component.other

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metes.worthit.core.designsystem.component.defaults.WorthItCardDefaults
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItCard(
    modifier: Modifier = Modifier,
    colors: CardColors = WorthItCardDefaults.colors(
        containerColor = AppTheme.colorScheme.primary.copy(alpha = 0.05f)
    ),
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = colors,
        onClick = onClick,
        content = content,
    )
}