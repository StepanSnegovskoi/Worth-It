package com.metes.worthit.core.ui

import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.error.FileError
import com.metes.worthit.core.domain.error.UnexpectedError

fun Error.toUiText(): UiText {
    val messageResId = when (this) {
        BusinessError.ItemFailedToSave -> R.string.failed_to_save_item
        BusinessError.ItemNameIsBlank -> R.string.item_name_is_blank
        BusinessError.ItemNotFound -> R.string.item_not_found
        BusinessError.ItemPurchaseDateInTheFuture -> R.string.item_purchase_date_in_the_future
        BusinessError.ItemImageFailedToSave -> R.string.failed_to_save_image
        BusinessError.ItemFailedToDelete -> R.string.item_failed_to_delete

        FileError.CompressionFailed -> R.string.failed_to_save_item
        FileError.FailedToDeleteFile -> R.string.failed_to_save_item
        FileError.FileNotFound -> R.string.failed_to_save_item
        FileError.UnsupportedUriScheme -> R.string.failed_to_save_item

        is UnexpectedError -> R.string.unexpected_error
    }
    return UiText.StringResource(messageResId)
}
