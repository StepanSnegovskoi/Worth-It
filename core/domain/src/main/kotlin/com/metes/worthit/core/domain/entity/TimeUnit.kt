package com.metes.worthit.core.domain.entity

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.temporal.ChronoUnit

// if you wanna do
enum class TimeUnit {
    DAY,
    WEEK,
    MONTH,
    YEAR,
}

fun TimeUnit.between(from: LocalDate, to: LocalDate): Int {
    return (when (this) {
        TimeUnit.DAY -> ChronoUnit.DAYS.between(from, to)
        TimeUnit.WEEK -> ChronoUnit.WEEKS.between(from, to)
        TimeUnit.MONTH -> ChronoUnit.MONTHS.between(from, to)
        TimeUnit.YEAR -> ChronoUnit.YEARS.between(from, to)
    }.coerceAtLeast(0) + 1).toInt()
}

fun TimeUnit.calculatePrice(
    price: BigDecimal,
    currentDate: LocalDate,
    dateOfPurchase: LocalDate
): BigDecimal {
    val daysBetween = BigDecimal.valueOf(
        between(
            from = dateOfPurchase,
            to = currentDate
        ).toLong()
    )

    if (daysBetween.equals(BigDecimal.ZERO)) {
        return price
    }

    return price.divide(daysBetween, 3, RoundingMode.HALF_UP)
}
