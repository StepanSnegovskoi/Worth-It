package com.metes.worthit.core.presentation

import android.content.Context
import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.error.FileError
import com.metes.worthit.core.domain.error.UnexpectedError
import com.metes.worthit.core.presentation.UiText.*

fun Error.toUiText(): UiText {
    return when (this) {
        BusinessError.ItemFailedToSave -> StringResource(R.string.failed_to_save_item)
        BusinessError.ItemNameIsBlank -> StringResource(R.string.item_name_is_blank)
        BusinessError.ItemNotFound -> StringResource(R.string.item_not_found)
        BusinessError.ItemPurchaseDateInTheFuture -> StringResource(R.string.item_purchase_date_in_the_future)
        BusinessError.ItemImageFailedToSave -> StringResource(R.string.failed_to_save_image)
        BusinessError.ItemFailedToDelete -> StringResource(R.string.item_failed_to_delete)
        BusinessError.ItemsFailedToDelete -> StringResource(R.string.an_error_occurred_while_deleting_items)
        BusinessError.ItemPriceCanContainOnlyNumbers -> StringResource(R.string.item_price_can_contain_only_numbers)
        BusinessError.ItemPriceCantBeNegative -> StringResource(R.string.item_price_cant_be_negative)
        is BusinessError.ItemPriceCantBeMoreThan -> StringResource(
            R.string.item_price_cant_be_more_than,
            listOf(this.price, this.maxCountOfDigitsInIntegerPrice)
        )

        FileError.CompressionFailed,
        FileError.FailedToDeleteFile,
        FileError.FileNotFound,
        FileError.UnsupportedUriScheme -> StringResource(R.string.failed_to_save_item)

        is UnexpectedError -> StringResource(R.string.unexpected_error)
    }
}

fun List<Error>.asCombinedString(context: Context, separator: String = "\n"): String {
    return joinToString(separator = separator) { it.toUiText().asString(context) }
}
