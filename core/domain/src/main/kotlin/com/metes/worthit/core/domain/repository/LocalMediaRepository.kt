package com.metes.worthit.core.domain.repository

import com.metes.worthit.core.domain.utils.Result

interface LocalMediaRepository {
    suspend fun saveImage(resourceIdentifier: String): Result<String, Exception>
    suspend fun deleteImage(path: String): Result<Unit, Exception>
}