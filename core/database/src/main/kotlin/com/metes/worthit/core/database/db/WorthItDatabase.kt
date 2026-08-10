package com.metes.worthit.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.metes.worthit.core.database.entity.ItemDbModel

@Database(entities = [ItemDbModel::class], version = 3, exportSchema = false)
abstract class WorthItDatabase : RoomDatabase() {

    abstract fun dao(): ItemsDao

    companion object {
        const val DATABASE_NAME = "database.db"
    }
}
