package com.metes.worthit.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.metes.worthit.core.common.IoDispatcher
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.domain.entity.UserPreferences
import com.metes.worthit.core.domain.utils.UserSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultUserSettings @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): UserSettings {

    override suspend fun saveCurrency(currency: Currency) = withContext<Unit>(ioDispatcher) {
        dataStore.edit { preferences ->
            preferences[KEY_CURRENCY_PREF] = currency.name
        }
    }

    override suspend fun saveThemeColor(themeColor: ThemeColor) = withContext<Unit>(ioDispatcher) {
        dataStore.edit { preferences ->
            preferences[KEY_THEME_COLOR_PREF] = themeColor.name
        }
    }

    override suspend fun saveThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[KEY_THEME_MODE_PREF] = themeMode.name
        }
    }

    override val preferences: Flow<UserPreferences> = dataStore.data.map { preferences ->
        val currencyName = preferences[KEY_CURRENCY_PREF]
        val themeColorName = preferences[KEY_THEME_COLOR_PREF]
        val themeModeName = preferences[KEY_THEME_MODE_PREF]

        UserPreferences(
            currency = Currency.fromNameOrDefault(currencyName),
            themeColor = ThemeColor.fromNameOrDefault(themeColorName),
            themeMode = ThemeMode.fromNameOrDefault(themeModeName),
        )
    }

    companion object {
        private const val KEY_CURRENCY = "currency_key"
        private const val KEY_THEME_COLOR = "theme_color"
        private const val KEY_THEME_MODE = "theme_mode"
        private val KEY_CURRENCY_PREF = stringPreferencesKey(KEY_CURRENCY)
        private val KEY_THEME_COLOR_PREF = stringPreferencesKey(KEY_THEME_COLOR)
        private val KEY_THEME_MODE_PREF = stringPreferencesKey(KEY_THEME_MODE)
    }
}
