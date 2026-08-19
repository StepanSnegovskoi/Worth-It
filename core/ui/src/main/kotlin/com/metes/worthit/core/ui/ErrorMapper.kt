package com.metes.worthit.core.ui

import android.content.Context
import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.error.FileError
import com.metes.worthit.core.domain.error.UnexpectedError

fun Error.toUiText(): UiText {
    return when (this) {
        BusinessError.ItemFailedToSave -> UiText.StringResource(R.string.failed_to_save_item)
        BusinessError.ItemNameIsBlank -> UiText.StringResource(R.string.item_name_is_blank)
        BusinessError.ItemNotFound -> UiText.StringResource(R.string.item_not_found)
        BusinessError.ItemPurchaseDateInTheFuture -> UiText.StringResource(R.string.item_purchase_date_in_the_future)
        BusinessError.ItemImageFailedToSave -> UiText.StringResource(R.string.failed_to_save_image)
        BusinessError.ItemFailedToDelete -> UiText.StringResource(R.string.item_failed_to_delete)
        is BusinessError.ItemPriceLengthCantBeMoreThan -> UiText.StringResource(
            R.string.item_price_length_cant_be_more_than,
            listOf(length)
        )

        BusinessError.ItemPriceInvalidFormat -> UiText.StringResource(R.string.item_price_can_contain_only_numbers)

        FileError.CompressionFailed,
        FileError.FailedToDeleteFile,
        FileError.FileNotFound,
        FileError.UnsupportedUriScheme -> UiText.StringResource(R.string.failed_to_save_item)

        is UnexpectedError -> UiText.StringResource(R.string.unexpected_error)
    }
}

fun List<Error>.asCombinedString(context: Context, separator: String = "\n"): String {
    return joinToString(separator = separator) { it.toUiText().asString(context) }
}
