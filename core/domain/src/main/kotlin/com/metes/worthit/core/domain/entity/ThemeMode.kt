package com.metes.worthit.core.domain.entity

enum class ThemeMode {
    DARK,
    LIGHT,
    SYSTEM;
    companion object {
        fun fromNameOrDefault(name: String?): ThemeMode {
            if (name== null) return SYSTEM

            return runCatching {
                valueOf(name.trim().uppercase())
            }.getOrDefault(SYSTEM)
        }
    }
}
