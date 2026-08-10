package com.metes.worthit.core.common

import java.time.LocalDate
import java.time.ZoneOffset

fun LocalDate.toEpochMilliOrNull(zoneOffset: ZoneOffset = ZoneOffset.UTC) = this
    .atStartOfDay(zoneOffset)
    .toInstant()
    .toEpochMilli()
