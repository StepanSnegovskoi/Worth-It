package com.metes.worthit.core.domain.entity

enum class ThemeColor {
    BLUE,
    PINK;
    companion object {
        fun fromNameOrDefault(name: String?): ThemeColor {
            if (name.isNullOrBlank()) return BLUE

            return runCatching {
                valueOf(name.trim().uppercase())
            }.getOrDefault(BLUE)
        }
    }
}
