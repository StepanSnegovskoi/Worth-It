package com.metes.worthit.core.designsystem.extensions

import androidx.compose.ui.graphics.Color
import com.metes.worthit.core.designsystem.theme.PrimaryThemeColor
import com.metes.worthit.core.designsystem.theme.darkBlueColorsScheme
import com.metes.worthit.core.designsystem.theme.darkPinkColorsScheme
import com.metes.worthit.core.designsystem.theme.lightBlueColorsScheme
import com.metes.worthit.core.designsystem.theme.lightPinkColorsScheme

fun PrimaryThemeColor.primaryColor(isDark: Boolean): Color = when (this) {
    PrimaryThemeColor.BLUE -> if (isDark) darkBlueColorsScheme.primary else lightBlueColorsScheme.primary
    PrimaryThemeColor.PINK -> if (isDark) darkPinkColorsScheme.primary else lightPinkColorsScheme.primary
}
