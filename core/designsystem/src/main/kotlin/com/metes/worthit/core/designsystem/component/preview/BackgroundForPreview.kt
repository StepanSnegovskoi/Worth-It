package com.metes.worthit.core.designsystem.component.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.designsystem.theme.PrimaryThemeColor

@Composable
fun BackgroundForPreview(
    modifier: Modifier = Modifier,
    transparent: Boolean = true,
    innerPadding: Dp = 8.dp,
    content: @Composable RowScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .background(Color.Transparent),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val previewConfigurations = buildList {
            PrimaryThemeColor.entries.forEach {
                add(true to it)
                add(false to it)
            }
        }

        previewConfigurations.forEach { (isDark, color) ->
            AppTheme(
                isDarkTheme = isDark,
                primaryThemeColor = color,
            ) {
                Surface(
                    color = if (transparent) Color.Transparent else AppTheme.colorScheme.background
                ) {
                    Row(
                        modifier = Modifier
                            .padding(innerPadding),
                    ) {
                        content()
                    }
                }
            }
        }
    }
}
