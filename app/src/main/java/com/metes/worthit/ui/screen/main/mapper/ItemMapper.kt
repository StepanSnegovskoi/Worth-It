package com.metes.worthit.ui.screen.main.mapper

import com.metes.worthit.domain.entity.Item
import com.metes.worthit.ui.screen.main.ItemUiModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

fun Item.toUiModel(currentDate: LocalDate): ItemUiModel {
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    val formattedDates = if (boughtAt != null) {
        "${boughtAt.format(formatter)} - ${currentDate.format(formatter)}"
    } else null

    val days = if (boughtAt != null) {
        (currentDate.toEpochDay() - boughtAt.toEpochDay() + 1).coerceAtLeast(1L)
    } else null

    val pricePerDayText = if (price != null && days != null) {
        val pricePerDay = price.toDouble() / days.toDouble()
        String.format(Locale.getDefault(), "%.2f", pricePerDay)
    } else null

    return ItemUiModel(
        id = id,
        name = name,
        localImagePath = imageLocalPath,
        formattedDates = formattedDates,
        daysCountText = days?.toString(),
        pricePerDayText = pricePerDayText
    )
}
