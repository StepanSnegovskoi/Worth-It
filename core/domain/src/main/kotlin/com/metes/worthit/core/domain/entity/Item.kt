package com.metes.worthit.core.domain.entity

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate

data class Item(
    val id: Int = DEFAULT_ID,
    val name: String,
    val price: BigDecimal?,
    val currency: Currency,
    val createdAt: Instant,
    val dateOfPurchase: LocalDate,
    val description: String?,
    val imageLocalPath: String?
) {
    companion object {
        const val DEFAULT_ID = 0
    }
}
