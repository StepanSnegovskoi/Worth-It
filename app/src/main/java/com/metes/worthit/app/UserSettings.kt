package com.metes.worthit.app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.metes.worthit.ui.entity.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettings @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun saveCurrency(currency: Currency) = withContext<Unit>(dispatcherProvider.io) {
        dataStore.edit { preferences ->
            preferences[KEY_CURRENCY_PREF] = currency.name
        }
    }

    fun getCurrencyName(): Flow<String> = dataStore.data.map { preferences ->
        val currencies = Currency.entries
        val currencyName = preferences[KEY_CURRENCY_PREF] ?: currencies.first().name
        currencyName
    }

    companion object {
        private const val KEY_CURRENCY = "currency_key"
        private val KEY_CURRENCY_PREF = stringPreferencesKey(KEY_CURRENCY)
    }
}