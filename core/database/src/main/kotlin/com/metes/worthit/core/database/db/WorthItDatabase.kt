package com.metes.worthit.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.metes.worthit.core.database.converter.InstantConverter
import com.metes.worthit.core.database.converter.LocalDateConverter
import com.metes.worthit.core.database.entity.ItemDbModel

@Database(entities = [ItemDbModel::class], version = 2, exportSchema = false)
@TypeConverters(value = [LocalDateConverter::class, InstantConverter::class])
internal abstract class WorthItDatabase : RoomDatabase() {

    abstract fun dao(): ItemsDao

    companion object {
        const val DATABASE_NAME = "database.db"
    }
}
