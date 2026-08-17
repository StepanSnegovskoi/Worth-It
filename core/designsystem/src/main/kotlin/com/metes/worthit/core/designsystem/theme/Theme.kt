package com.metes.worthit.core.designsystem.theme

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

    CompositionLocalProvider(
        LocalAppTheme provides theme,
        LocalAppShape provides shape,
        content = content
    )
}

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppTheme.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current
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
