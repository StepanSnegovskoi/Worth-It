package com.metes.worthit.domain.repository

import com.metes.worthit.domain.utils.Result

interface StorageRepository<in T, out R> {
    suspend fun save(t: T): R
    suspend fun delete(path: String): Result<Unit, Exception>
}