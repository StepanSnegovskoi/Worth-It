package com.metes.worthit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.metes.worthit.domain.entity.Item

@Entity(tableName = "items")
data class ItemDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "local_path")
    val localPath: String?
)

fun Item.toDbModel() = ItemDbModel(
    id = id,
    name = name,
    description = description,
    localPath = localPath
)

fun ItemDbModel.toEntity() = Item(
    id = id,
    name = name,
    description = description,
    localPath = localPath
)

fun List<ItemDbModel>.toEntities() = map { it.toEntity() }