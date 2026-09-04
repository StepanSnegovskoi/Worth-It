package com.metes.worthit.core.presentation

import androidx.annotation.StringRes
import com.metes.worthit.core.designsystem.theme.PrimaryThemeColor
import com.metes.worthit.core.domain.entity.ThemeColor

val PrimaryThemeColor.toThemeColor: ThemeColor
    get() = when (this) {
        PrimaryThemeColor.BLUE -> ThemeColor.BLUE
        PrimaryThemeColor.PINK -> ThemeColor.PINK
    }

val PrimaryThemeColor.nameStringRes: Int
    @StringRes get() = toThemeColor.nameStringRes
