package com.metes.worthit.domain.repository

interface StorageRepository<in T, out R> {
    suspend fun save(t: T): R
    suspend fun delete(path: String)
}