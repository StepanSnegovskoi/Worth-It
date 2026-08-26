package com.metes.worthit.core.common

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

fun LocalDate.toUtcEpochMilli() = this
    .atStartOfDay(ZoneOffset.UTC)
    .toInstant()
    .toEpochMilli()

fun Long.toLocalDateFromUtc(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDate()

