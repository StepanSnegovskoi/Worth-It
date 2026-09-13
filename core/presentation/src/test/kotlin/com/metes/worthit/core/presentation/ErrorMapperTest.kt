package com.metes.worthit.core.presentation

import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.error.FileError
import com.metes.worthit.core.domain.error.UnexpectedError
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertIs

class ErrorMapperTest {

    @Test
    fun `verify static error mappings to string resources`() {
        val staticMappings: Map<Error, Int> = mapOf(
            BusinessError.ItemFailedToSave to R.string.failed_to_save_item,
            BusinessError.ItemNameIsBlank to R.string.item_name_is_blank,
            BusinessError.ItemNotFound to R.string.item_not_found,
            BusinessError.ItemPurchaseDateInTheFuture to R.string.item_purchase_date_in_the_future,
            BusinessError.ItemImageFailedToSave to R.string.failed_to_save_image,
            BusinessError.ItemFailedToDelete to R.string.item_failed_to_delete,
            BusinessError.ItemsFailedToDelete to R.string.an_error_occurred_while_deleting_items,
            BusinessError.ItemPriceCanContainOnlyNumbers to R.string.item_price_can_contain_only_numbers,
            BusinessError.ItemPriceCantBeNegative to R.string.item_price_cant_be_negative,
            FileError.CompressionFailed to R.string.failed_to_save_item,
            FileError.FailedToDeleteFile to R.string.failed_to_save_item,
            FileError.FileNotFound to R.string.failed_to_save_item,
            FileError.UnsupportedUriScheme to R.string.failed_to_save_item,
        )

        staticMappings.forEach { (error, expectedResId) ->
            val uiText = error.toUiText()
            assertIs<UiText.StringResource>(uiText)
            assertEquals(expectedResId, uiText.resId)
        }
    }

    @Test
    fun `verify ItemPriceCantBeMoreThan mapping with arguments`() {
        val error =
            BusinessError.ItemPriceCantBeMoreThan(price = "500", maxCountOfDigitsInIntegerPrice = 4)
        val uiText = error.toUiText()

        assertIs<UiText.StringResource>(uiText)
        assertEquals(R.string.item_price_cant_be_more_than, uiText.resId)
        assertEquals(listOf("500", 4), uiText.args)
    }

    @Test
    fun `verify UnexpectedError mapping`() {
        val error = UnexpectedError(RuntimeException())
        val uiText = error.toUiText()

        assertIs<UiText.StringResource>(uiText)
        assertEquals(R.string.unexpected_error, uiText.resId)
    }
}
