package com.metes.worthit.core.testing

import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.domain.entity.UserPreferences
import com.metes.worthit.core.domain.utils.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

val defaultUserPreferences = UserPreferences(
    currency = Currency.fromNameOrDefault(null),
    themeColor = ThemeColor.fromNameOrDefault(null),
    themeMode = ThemeMode.fromNameOrDefault(null),
)

class FakeUserSettings : UserSettings {

    private val _preferences = MutableStateFlow(defaultUserPreferences)

    override val preferences: Flow<UserPreferences> = _preferences.asStateFlow()

    override suspend fun saveCurrency(currency: Currency) {
        _preferences.update { previousPreferences ->
            previousPreferences.copy(currency = currency)
        }
    }

    override suspend fun saveThemeColor(themeColor: ThemeColor) {
        _preferences.update { previousPreferences ->
            previousPreferences.copy(themeColor = themeColor)
        }
    }

    override suspend fun saveThemeMode(themeMode: ThemeMode) {
        _preferences.update { previousPreferences ->
            previousPreferences.copy(themeMode = themeMode)
        }
    }
}
