package com.metes.worthit.core.presentation

import androidx.annotation.StringRes
import com.metes.worthit.core.designsystem.theme.PrimaryThemeColor
import com.metes.worthit.core.domain.entity.ThemeColor

fun ThemeColor.toPrimaryThemeColor(): PrimaryThemeColor = when (this) {
    ThemeColor.BLUE -> PrimaryThemeColor.BLUE
    ThemeColor.PINK -> PrimaryThemeColor.PINK
}

val ThemeColor.nameStringRes: Int
    @StringRes get() = when (this) {
        ThemeColor.BLUE -> R.string.blue
        ThemeColor.PINK -> R.string.pink
    }
