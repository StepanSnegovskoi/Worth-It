package com.metes.worthit.domain.entity

import com.metes.worthit.ui.entity.Currency
import java.time.Instant
import java.time.LocalDate


data class Item(
    val id: Int = DEFAULT_ID,
    val name: String,
    val price: Long?,
    val currency: Currency?,
    val createdAt: Instant,
    val boughtAt: LocalDate?,
    val description: String?,
    val imageLocalPath: String?
) {
    companion object {
        private const val DEFAULT_ID = 0
    }
}
