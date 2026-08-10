package com.metes.worthit.feature.items.mapper

import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.feature.items.ItemUiModel

fun Item.toUiModel(): ItemUiModel {

    return ItemUiModel(
        id = id,
        name = name,
        localImagePath = imageLocalPath,
        dateOfPurchase = dateOfPurchase,
    )
}

fun List<Item>.toUiModels(): List<ItemUiModel> {
    return map { it.toUiModel() }
}
