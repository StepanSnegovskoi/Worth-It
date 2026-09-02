package com.metes.worthit.core.designsystem.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.metes.worthit.core.designsystem.extensions.colorScheme

@Composable
fun AppTheme(
    isDarkTheme: Boolean,
    primaryThemeColor: PrimaryThemeColor,
    content: @Composable () -> Unit
) {
    val theme = primaryThemeColor.colorScheme(isDarkTheme)

    val shape = AppShape()
    val alpha = AppAlpha()
    val rippleConfiguration = RippleConfiguration(color = theme.primary)
    val indication = ripple()

    CompositionLocalProvider(
        LocalAppTheme provides theme,
        LocalAppShape provides shape,
        LocalAppAlpha provides alpha,
        LocalRippleConfiguration provides rippleConfiguration,
        LocalIndication provides indication,
        LocalIsDarkTheme provides isDarkTheme,
        LocalContentColor provides theme.onBackground,
        content = content
    )
}

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppTheme.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current

    val alpha: AppAlpha
        @Composable get() = LocalAppAlpha.current

    val isDarkTheme: Boolean
        @Composable get() = LocalIsDarkTheme.current
}

val LocalAppTheme = staticCompositionLocalOf {
    AppColorScheme(
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        primaryContainer = Color.Unspecified,
        onPrimaryContainer = Color.Unspecified,
        secondary = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        error = Color.Unspecified,
        correct = Color.Unspecified,
    )
}

val LocalIsDarkTheme = staticCompositionLocalOf {
    false
}
