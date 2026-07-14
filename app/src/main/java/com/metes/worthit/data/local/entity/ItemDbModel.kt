package com.metes.worthit.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.metes.worthit.domain.entity.Item
import com.metes.worthit.ui.entity.Currency
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = "items")
data class ItemDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Long?,
    @ColumnInfo(name = "currency_name")
    val currencyName: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "bought_at_day")
    val boughtAtDay: Long?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "image_local_path")
    val imageLocalPath: String?
)

fun Item.toDbModel() = ItemDbModel(
    id = id,
    name = name,
    price = price,
    currencyName = currency.name,
    createdAt = createdAt.toEpochMilli(),
    boughtAtDay = boughtAt?.toEpochDay(),
    description = description,
    imageLocalPath = imageLocalPath
)

fun ItemDbModel.toEntity() = Item(
    id = id,
    name = name,
    price = price,
    currency = Currency.fromNameOrFirst(currencyName),
    createdAt = Instant.ofEpochMilli(createdAt),
    boughtAt = boughtAtDay?.let { LocalDate.ofEpochDay(it) },
    description = description,
    imageLocalPath = imageLocalPath
)

fun List<ItemDbModel>.toEntities() = map { it.toEntity() }