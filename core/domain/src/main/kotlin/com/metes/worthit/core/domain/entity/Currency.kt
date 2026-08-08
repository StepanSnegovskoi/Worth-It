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
            val defaultCurrency = EUR
            if (name.isNullOrBlank()) return defaultCurrency

            return runCatching {
                valueOf(name.trim().uppercase())
            }.getOrElse {
                defaultCurrency
            }
        }
    }
}
