package com.metes.worthit.core.designsystem.component.surface

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.defaults.WorthItCardDefaults
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItCard(
    modifier: Modifier = Modifier,
    colors: CardColors = WorthItCardDefaults.colors(),
    shape: Shape = WorthItCardDefaults.shape,
    elevation: Dp = WorthItCardDefaults.elevation,
    ambientShadowColor: Color = Color.Transparent,
    spotShadowColor: Color = AppTheme.colorScheme.primary,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .shadow(
                elevation = elevation,
                shape = shape,
                ambientColor = ambientShadowColor,
                spotColor = spotShadowColor
            )
            .clip(shape)
            .drawBehind {
                drawRect(color = colors.containerColor)
            },
    ) {
        CompositionLocalProvider(
            value = LocalContentColor provides colors.contentColor,
            content = content
        )
    }
}

@Composable
fun WorthItCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: CardColors = WorthItCardDefaults.colors(),
    shape: Shape = WorthItCardDefaults.shape,
    elevation: Dp = WorthItCardDefaults.elevation,
    ambientShadowColor: Color = Color.Transparent,
    spotShadowColor: Color = AppTheme.colorScheme.primary,
    onLongClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .shadow(
                elevation = elevation,
                shape = shape,
                ambientColor = ambientShadowColor,
                spotColor = spotShadowColor
            )
            .clip(shape)
            .drawBehind {
                drawRect(color = colors.containerColor)
            }
            .combinedClickable(
                onClick = onClick,
                onLongClick = {
                    onLongClick?.invoke()
                }
            ),
    ) {
        CompositionLocalProvider(
            value = LocalContentColor provides colors.contentColor,
            content = content
        )
    }
}

@Preview
@Composable
fun WorthItCardPreviewClickable(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            Row(modifier = Modifier.padding(8.dp)) {
                WorthItCard(
                    onClick = {},
                    content = {
                        WorthItText(
                            text = "Hello World",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun WorthItCardPreviewNotClickable(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            Row(modifier = Modifier.padding(8.dp)) {
                WorthItCard(
                    content = {
                        WorthItText(
                            text = "Hello World",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                )
            }
        }
    }
}
