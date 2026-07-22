package com.metes.worthit.core.domain.repository

import com.metes.worthit.core.domain.utils.Result

interface StorageRepository<in T, out R> {
    suspend fun save(t: T): R
    suspend fun delete(path: String): Result<Unit, Exception>
}