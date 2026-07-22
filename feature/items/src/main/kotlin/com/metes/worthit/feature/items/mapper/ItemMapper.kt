package com.metes.worthit.feature.items.mapper

import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.feature.items.ItemUiModel
import java.time.LocalDate

fun Item.toUiModel(currentDate: LocalDate): ItemUiModel {
    val daysCount = boughtAt?.let {
        (currentDate.toEpochDay() - it.toEpochDay() + 1).coerceAtLeast(1L)
    }

    val pricePerDay = price?.let { price ->
        daysCount?.let { daysCount ->
            price.toDouble() / daysCount.toDouble()
        }
    }

    return ItemUiModel(
        id = id,
        name = name,
        localImagePath = imageLocalPath,
        boughtAt = boughtAt,
        daysCount = daysCount,
        pricePerDay = pricePerDay
    )
}

fun List<Item>.toUiModels(
    currentDate: LocalDate
): List<ItemUiModel> {
    return map { it.toUiModel(currentDate) }
}
