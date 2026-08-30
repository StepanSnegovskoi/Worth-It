package com.metes.worthit.core.presentation

import com.metes.worthit.core.designsystem.theme.PrimaryThemeColor
import com.metes.worthit.core.domain.entity.ThemeColor

fun ThemeColor.toPrimaryThemeColor(): PrimaryThemeColor = when (this) {
    ThemeColor.BLUE -> PrimaryThemeColor.BLUE
    ThemeColor.PINK -> PrimaryThemeColor.PINK
}
