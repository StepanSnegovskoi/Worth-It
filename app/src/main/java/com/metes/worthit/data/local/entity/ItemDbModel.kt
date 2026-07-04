package com.metes.worthit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
)
