package com.metes.worthit.domain.entity

data class Item(
    val id: Int = DEFAULT_ID,
    val name: String,
    val description: String?,
    val localPath: String?
) {
    companion object {
        private const val DEFAULT_ID = 0
    }
}
