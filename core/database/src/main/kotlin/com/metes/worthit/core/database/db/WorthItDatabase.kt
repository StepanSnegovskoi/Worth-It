package com.metes.worthit.core.database.db

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.metes.worthit.core.database.converter.BigDecimalConverter
import com.metes.worthit.core.database.converter.InstantConverter
import com.metes.worthit.core.database.converter.LocalDateConverter
import com.metes.worthit.core.database.entity.ItemDbModel

@Database(entities = [ItemDbModel::class], version = 1, exportSchema = false)
@ColumnTypeConverters(
    value = [
        LocalDateConverter::class,
        InstantConverter::class,
        BigDecimalConverter::class
    ]
)
internal abstract class WorthItDatabase : RoomDatabase() {

    abstract fun dao(): ItemsDao

    companion object {
        const val DATABASE_NAME = "database.db"
    }
}
