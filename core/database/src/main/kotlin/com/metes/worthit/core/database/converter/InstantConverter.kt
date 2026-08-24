package com.metes.worthit.core.database.converter

import androidx.room.TypeConverter
import java.time.Instant

class InstantConverter {

    @TypeConverter
    fun instantToEpochMilli(instant: Instant): Long {
        return instant.toEpochMilli()
    }

    @TypeConverter
    fun epochMilliToInstant(epochMilli: Long): Instant {
        return Instant.ofEpochMilli(epochMilli)
    }
}
