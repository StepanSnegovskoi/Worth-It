package com.metes.worthit.core.domain.error

sealed interface Error

data class UnexpectedError(val e: Exception) : Error

sealed interface BusinessError : Error {
    data object ItemNameIsBlank : BusinessError
    data object ItemNotFound : BusinessError
    data object ItemFailedToSave : BusinessError
    data object ItemFailedToDelete : BusinessError
    data object ItemImageFailedToSave : BusinessError
    data object ItemPurchaseDateInTheFuture : BusinessError
    data object ItemPriceCanContainOnlyNumbers : BusinessError
    data object ItemPriceCantBeNegative : BusinessError
}

sealed interface FileError : Error {
    data object FileNotFound : Error
    data object CompressionFailed : Error
    data object UnsupportedUriScheme : Error
    data object FailedToDeleteFile : Error
}
