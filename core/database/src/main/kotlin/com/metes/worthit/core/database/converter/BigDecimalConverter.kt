package com.metes.worthit.core.database.converter

import androidx.room3.ColumnTypeConverter
import java.math.BigDecimal

class BigDecimalConverter {

    @ColumnTypeConverter
    fun bigDecimalToString(bigDecimal: BigDecimal?): String? {
        return bigDecimal?.toString()
    }

    @ColumnTypeConverter
    fun stringToBigDecimal(string: String?): BigDecimal? {
        return string?.toBigDecimalOrNull()
    }
}
