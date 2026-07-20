package com.metes.worthit.ui.screen.main.mapper

import com.metes.worthit.domain.entity.Item
import com.metes.worthit.ui.screen.main.ItemUiModel
import java.time.LocalDate

fun Item.toUiModel(currentDate: LocalDate): ItemUiModel {
    val daysCount = if (boughtAt != null) {
        (currentDate.toEpochDay() - boughtAt.toEpochDay() + 1).coerceAtLeast(1L)
    } else null

    val pricePerDay = if (price != null && daysCount != null) {
        price.toDouble() / daysCount.toDouble()
    } else null

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
