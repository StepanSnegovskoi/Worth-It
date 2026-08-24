package com.metes.worthit.core.common

import java.math.RoundingMode
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Locale

fun LocalDate.toUtcEpochMilli() = this
    .atStartOfDay(ZoneOffset.UTC)
    .toInstant()
    .toEpochMilli()

fun Long.toLocalDateFromUtc(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDate()

fun Double.toAmountString(): String {
    val format = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        roundingMode = RoundingMode.HALF_UP
    }
    return format.format(this)
}
