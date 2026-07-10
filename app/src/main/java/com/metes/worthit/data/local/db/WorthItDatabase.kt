package com.metes.worthit.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.metes.worthit.data.local.dao.ItemsDao
import com.metes.worthit.data.local.entity.ItemDbModel

@Database(entities = [ItemDbModel::class], version = 2, exportSchema = false)
abstract class WorthItDatabase : RoomDatabase() {

    abstract fun dao(): ItemsDao

    companion object {
        const val DATABASE_NAME = "database.db"
    }
}