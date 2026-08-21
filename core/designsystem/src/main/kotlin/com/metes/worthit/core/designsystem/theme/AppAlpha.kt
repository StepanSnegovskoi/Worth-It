package com.metes.worthit.core.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class AppAlpha(
    val high: Float = 1f,
    val medium: Float = 0.75f,
    val low: Float = 0.25f,
    val extraLow: Float = 0.15f,
    val hover: Float = 0.08f,
)

val LocalAppAlpha = staticCompositionLocalOf {
    AppAlpha()
}
