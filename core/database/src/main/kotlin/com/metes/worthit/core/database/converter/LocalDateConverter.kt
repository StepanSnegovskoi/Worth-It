package com.metes.worthit.core.database.converter

import androidx.room3.ColumnTypeConverter
import java.time.LocalDate

class LocalDateConverter {

    @ColumnTypeConverter
    fun localDateToEpochDay(date: LocalDate): Long {
        return date.toEpochDay()
    }

    @ColumnTypeConverter
    fun epochDayToLocalDate(epochDay: Long): LocalDate {
        return LocalDate.ofEpochDay(epochDay)
    }
}
