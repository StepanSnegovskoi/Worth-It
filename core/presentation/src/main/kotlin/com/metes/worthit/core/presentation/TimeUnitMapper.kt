package com.metes.worthit.core.presentation

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.metes.worthit.core.domain.entity.TimeUnit

val TimeUnit.nameStringRes
    @StringRes get() = when (this) {
        TimeUnit.DAY -> R.string.time_unit_day
        TimeUnit.WEEK -> R.string.time_unit_week
        TimeUnit.MONTH -> R.string.time_unit_month
        TimeUnit.YEAR -> R.string.time_unit_year
    }

val TimeUnit.pricePerPluralRes
    @PluralsRes get() = when (this) {
        TimeUnit.DAY -> R.plurals.price_per_day
        TimeUnit.WEEK -> R.plurals.price_per_week
        TimeUnit.MONTH -> R.plurals.price_per_month
        TimeUnit.YEAR -> R.plurals.price_per_year
    }
