package com.metes.worthit.core.presentation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.metes.worthit.core.domain.entity.ThemeMode

val ThemeMode.nameStringRes: Int
    @StringRes get() = when (this) {
        ThemeMode.DARK -> R.string.dark
        ThemeMode.LIGHT -> R.string.light
        ThemeMode.SYSTEM -> R.string.system
    }

val ThemeMode.linearBrushGradient: Brush
    get() = when (this) {
        ThemeMode.DARK -> Brush.linearGradient(listOf(Color.Black, Color.Black))
        ThemeMode.LIGHT -> Brush.linearGradient(listOf(Color.White, Color.White))
        ThemeMode.SYSTEM -> Brush.linearGradient(
            0.0f to Color.White,
            0.5f to Color.White,
            0.5f to Color.Black,
            1f to Color.Black,
        )
    }
