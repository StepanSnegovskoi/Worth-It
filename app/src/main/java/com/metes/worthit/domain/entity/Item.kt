package com.metes.worthit.domain.entity

import java.time.Instant


data class Item(
    val id: Int = DEFAULT_ID,
    val name: String,
    val price: Long?,
    val createdAt: Instant,
    val boughtAt: Instant?,
    val description: String?,
    val imageLocalPath: String?
) {
    companion object {
        private const val DEFAULT_ID = 0
    }
}
