package com.metes.worthit.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.metes.worthit.core.common.IoDispatcher
import com.metes.worthit.core.domain.entity.Currency
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

    override fun getCurrencyName(): Flow<String> = dataStore.data.map { preferences ->
        val currencies = Currency.entries
        val currencyName = preferences[KEY_CURRENCY_PREF] ?: currencies.first().name
        currencyName
    }

    companion object {
        private const val KEY_CURRENCY = "currency_key"
        private val KEY_CURRENCY_PREF = stringPreferencesKey(KEY_CURRENCY)
    }
}
