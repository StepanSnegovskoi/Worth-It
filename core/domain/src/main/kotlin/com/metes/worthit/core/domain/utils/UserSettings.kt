package com.metes.worthit.core.domain.utils

import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.entity.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserSettings {
    val preferences: Flow<UserPreferences>
    suspend fun saveCurrency(currency: Currency)
    suspend fun saveThemeColor(themeColor: ThemeColor)
}