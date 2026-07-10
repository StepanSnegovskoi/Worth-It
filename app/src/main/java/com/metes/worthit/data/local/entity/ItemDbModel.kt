package com.metes.worthit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.metes.worthit.domain.entity.Item

@Entity(tableName = "items")
data class ItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val localPath: String?
)

fun Item.toDbModel() = ItemDbModel(
    id = id,
    name = name,
    localPath = localPath
)

fun ItemDbModel.toEntity() = Item(
    id = id,
    name = name,
    localPath = localPath
)

fun List<ItemDbModel>.toEntities() = map { it.toEntity() }