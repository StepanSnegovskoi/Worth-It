package com.metes.worthit.core.domain.utils

import com.metes.worthit.core.domain.entity.Currency
import kotlinx.coroutines.flow.Flow

interface UserSettings {
    suspend fun saveCurrency(currency: Currency)
    fun getCurrencyName(): Flow<String>
}