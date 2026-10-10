package com.metes.worthit.core.database.converter

import androidx.room3.ColumnTypeConverter
import java.time.Instant

class InstantConverter {

    @ColumnTypeConverter
    fun instantToEpochMilli(instant: Instant): Long = instant.toEpochMilli()

    @ColumnTypeConverter
    fun epochMilliToInstant(epochMilli: Long): Instant = Instant.ofEpochMilli(epochMilli)
}
