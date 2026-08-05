package com.metes.worthit.core.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.metes.worthit.core.domain.entity.Currency

val Currency.iconResId: Int
    @DrawableRes get() = when (this) {
        Currency.EUR ->  R.drawable.euro_24dp
        Currency.USD -> R.drawable.usd_24dp
        Currency.GBP -> R.drawable.gbp_24dp
        Currency.JPY -> R.drawable.jpy_24dp
        Currency.INR -> R.drawable.inr_24dp
        Currency.CNY -> R.drawable.cny_24dp
    }

val Currency.titleResId: Int
    @StringRes get() = when (this) {
        Currency.EUR -> R.string.currency_eur
        Currency.USD -> R.string.currency_usd
        Currency.GBP -> R.string.currency_gbp
        Currency.JPY -> R.string.currency_jpy
        Currency.INR -> R.string.currency_inr
        Currency.CNY -> R.string.chinese_yuan
    }