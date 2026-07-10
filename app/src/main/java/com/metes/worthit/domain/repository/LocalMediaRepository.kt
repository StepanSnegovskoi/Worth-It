package com.metes.worthit.domain.repository

import com.metes.worthit.domain.utils.Result

interface LocalMediaRepository {
    suspend fun saveImage(resourceIdentifier: String): Result<String, Exception>
    suspend fun deleteImage(path: String)
}