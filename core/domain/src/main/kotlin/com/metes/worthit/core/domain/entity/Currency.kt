package com.metes.worthit.core.domain.entity

enum class Currency {
    EUR,
    USD,
    GBP,
    JPY,
    INR,
    CNY;

    companion object {
        fun fromNameOrDefault(name: String?): Currency {
            if (name.isNullOrBlank()) return EUR

            return runCatching {
                valueOf(name.trim().uppercase())
            }.getOrElse {
                EUR
            }
        }
    }
}