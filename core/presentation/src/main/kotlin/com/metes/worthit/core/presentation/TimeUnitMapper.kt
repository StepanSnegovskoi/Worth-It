package com.metes.worthit.core.presentation

import com.metes.worthit.core.domain.entity.TimeUnit

val TimeUnit.stringRes
    get() = when (this) {
        TimeUnit.DAY -> R.string.time_unit_day
        TimeUnit.WEEK -> R.string.time_unit_week
        TimeUnit.MONTH -> R.string.time_unit_month
        TimeUnit.YEAR -> R.string.time_unit_year
    }
