package com.metes.worthit.core.designsystem.theme

import androidx.compose.ui.graphics.Color

internal val White = Color(0xFFFFFFFF)

internal val BluePrimaryLight = Color(0xFF2563EB)
internal val BluePrimaryDark = Color(0xFF60A5FA)
internal val OnPrimaryBlueLight = Color(0xFF0F172A)
internal val OnPrimaryBlueDark = Color(0xFF0F172A)

internal val PrimaryContainerBlueLight = Color(0xFFB3D2FF)
internal val OnPrimaryContainerBlueLight = Color(0xFF1E3A8A)

internal val PrimaryContainerBlueDark = Color(0xFF253E6B)
internal val OnPrimaryContainerBlueDark = Color(0xFFDBEAFE)

internal val BackgroundBlueLight = Color(0xFFECF3FA)
internal val BackgroundBlueDark = Color(0xFF0F172A)

internal val TextBlueLight = Color(0xFF0F172A)
internal val TextBlueDark = Color(0xFFF1F5F9)

internal val SecondaryBlueLight = Color(0xFF3C4D62)
internal val SecondaryBlueDark = Color(0xFF94A3B8)

internal val SurfaceBlueLight= Color(0xFFC9E1FF)
internal val SurfaceBlueDark = Color(0xFF1E293B)

internal val PinkPrimaryLight = Color(0xFFCD25EB)
internal val PinkPrimaryDark = Color(0xFFE360FA)
internal val OnPrimaryPinkDark = Color(0xFF250F2A)
internal val OnPrimaryPinkLight = Color(0xFFFFFFFF)

internal val PrimaryContainerPinkLight = Color(0xFFF6CBFD)
internal val OnPrimaryContainerPinkLight = Color(0xFF7A1E8A)

internal val PrimaryContainerPinkDark = Color(0xFF64256B)
internal val OnPrimaryContainerPinkDark = Color(0xFFDBEAFE)

internal val BackgroundPinkLight = Color(0xFFECF3FA)
internal val BackgroundPinkDark = Color(0xFF270F2A)

internal val TextPinkLight = Color(0xFF0F172A)
internal val TextPinkDark = Color(0xFFF1F5F9)

internal val SecondaryPinkLight = Color(0xFF524454)
internal val SecondaryPinkDark = Color(0xFF94A3B8)

internal val SurfacePinkLight = Color(0xFFF6E3FA)
internal val SurfacePinkDark = Color(0xFF361E3B)

internal val Error = Color(0xFFA8203C)

internal val Correct = Color(0xFF2FEC36)

enum class PrimaryThemeColor {
    BLUE, PINK
}

val lightBlueColorsScheme = AppColorScheme(
    background = BackgroundBlueLight,
    onBackground = TextBlueLight,
    primary = BluePrimaryLight,
    onPrimary = OnPrimaryBlueLight,
    primaryContainer = PrimaryContainerBlueLight,
    onPrimaryContainer = OnPrimaryContainerBlueLight,
    secondary = SecondaryBlueLight,
    surface = SurfaceBlueLight,
    onSurface = TextBlueLight,
    error = Error,
    correct = Correct,
)

val darkBlueColorsScheme = AppColorScheme(
    background = BackgroundBlueDark,
    onBackground = TextBlueDark,
    primary = BluePrimaryDark,
    onPrimary = OnPrimaryBlueDark,
    primaryContainer = PrimaryContainerBlueDark,
    onPrimaryContainer = OnPrimaryContainerBlueDark,
    secondary = SecondaryBlueDark,
    surface = SurfaceBlueDark,
    onSurface = TextBlueDark,
    error = Error,
    correct = Correct,
)

val darkPinkColorsScheme = AppColorScheme(
    background = BackgroundPinkDark,
    onBackground = TextPinkDark,
    primary = PinkPrimaryDark,
    onPrimary = OnPrimaryPinkDark,
    primaryContainer = PrimaryContainerPinkDark,
    onPrimaryContainer = OnPrimaryContainerPinkDark,
    secondary = SecondaryPinkDark,
    surface = SurfacePinkDark,
    onSurface = TextPinkDark,
    error = Error,
    correct = Correct,
)

val lightPinkColorsScheme = AppColorScheme(
    background = BackgroundPinkLight,
    onBackground = TextPinkLight,
    primary = PinkPrimaryLight,
    onPrimary = OnPrimaryPinkLight,
    primaryContainer = PrimaryContainerPinkLight,
    onPrimaryContainer = OnPrimaryContainerPinkLight,
    secondary = SecondaryPinkLight,
    surface = SurfacePinkLight,
    onSurface = TextPinkLight,
    error = Error,
    correct = Correct,
)
