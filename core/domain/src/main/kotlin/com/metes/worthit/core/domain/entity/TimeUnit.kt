package com.metes.worthit.core.domain.entity

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

enum class TimeUnit {
    DAY,
    WEEK,
    MONTH,
    YEAR,
}

fun TimeUnit.between(from: LocalDate, to: LocalDate): Long {
    return when (this) {
        TimeUnit.DAY -> ChronoUnit.DAYS.between(from, to)
        TimeUnit.WEEK -> ChronoUnit.WEEKS.between(from, to)
        TimeUnit.MONTH -> ChronoUnit.MONTHS.between(from, to)
        TimeUnit.YEAR -> ChronoUnit.YEARS.between(from, to)
    }.coerceAtLeast(0) + 1
}

fun TimeUnit.calculatePrice(
    price: Long,
    currentDate: LocalDate,
    dateOfPurchase: LocalDate
): Long = price / between(
    from = dateOfPurchase,
    to = currentDate
)
