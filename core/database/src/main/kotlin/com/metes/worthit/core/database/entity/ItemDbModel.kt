package com.metes.worthit.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.Item
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
    val currencyName: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: Instant,
    @ColumnInfo(name = "date_of_purchase")
    val dateOfPurchase: LocalDate,
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
    createdAt = createdAt,
    dateOfPurchase = dateOfPurchase,
    description = description,
    imageLocalPath = imageLocalPath
)

fun ItemDbModel.toEntity() = Item(
    id = id,
    name = name,
    price = price,
    currency = Currency.fromNameOrDefault(currencyName),
    createdAt = createdAt,
    dateOfPurchase = dateOfPurchase,
    description = description,
    imageLocalPath = imageLocalPath
)

fun List<ItemDbModel>.toEntities() = map { it.toEntity() }
