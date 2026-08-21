package com.metes.worthit.core.designsystem.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val lightColorsScheme = AppColorScheme(
    background = BackgroundLight,
    onBackground = TextLight,
    primary = BluePrimaryLight,
    onPrimary = White,
    secondary = SecondaryLight,
    surface = White,
    onSurface = TextLight,
    error = Error,
    correct = Correct,
)

val darkColorsScheme = AppColorScheme(
    background = BackgroundDark,
    onBackground = TextDark,
    primary = BluePrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = SecondaryDark,
    surface = SurfaceDark,
    onSurface = TextDark,
    error = Error,
    correct = Correct,
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val theme = if (isDarkTheme) darkColorsScheme else lightColorsScheme
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
}

val LocalAppTheme = staticCompositionLocalOf {
    AppColorScheme(
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        error = Color.Unspecified,
        correct = Correct,
    )
}
