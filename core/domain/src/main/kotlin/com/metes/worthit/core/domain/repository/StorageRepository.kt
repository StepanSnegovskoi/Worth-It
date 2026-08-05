package com.metes.worthit.core.domain.repository

import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.utils.Result
import java.util.UUID

interface StorageRepository {
    suspend fun save(
        imagePath: String,
        fileName: String = "IMG_${UUID.randomUUID()}",
    ): Result<String, Error>

    suspend fun delete(path: String): Result<Unit, Error>
}
