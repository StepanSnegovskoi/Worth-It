package com.metes.worthit.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.domain.entity.UserPreferences
import com.metes.worthit.core.domain.utils.UserSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultUserSettingsTest {
    private lateinit var userSettings: UserSettings
    private var dispatcher: CoroutineDispatcher = StandardTestDispatcher(TestCoroutineScheduler())
    private val defaultUserSettings = UserPreferences(
        currency = Currency.EUR,
        themeColor = ThemeColor.BLUE,
        themeMode = ThemeMode.SYSTEM,
    )

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        userSettings = DefaultUserSettings(
            dataStore = PreferenceDataStoreFactory.create {
                context.preferencesDataStoreFile("test_user_settings")
            },
            ioDispatcher = dispatcher,
        )
    }

    @Test
    fun `preferences returns correct default settings if nothing was saved`() =
        runTest(dispatcher) {
            userSettings.preferences.test {
                assertEquals(defaultUserSettings, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `saveCurrency saves Currency correctly`() = runTest(dispatcher) {
        userSettings.preferences.test {
            assertEquals(defaultUserSettings, awaitItem())
            Currency.entries.forEach { currency ->
                userSettings.saveCurrency(currency)
                assertEquals(defaultUserSettings.copy(currency = currency), awaitItem())
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `preferences saveThemeColor saves ThemeColor correctly`() = runTest(dispatcher) {
        userSettings.preferences.test {
            assertEquals(defaultUserSettings, awaitItem())
            ThemeColor.entries.forEach { themeColor ->
                userSettings.saveThemeColor(themeColor)
                assertEquals(defaultUserSettings.copy(themeColor = themeColor), awaitItem())
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `saveThemeMode saves ThemeMode correctly`() = runTest(dispatcher) {
        userSettings.preferences.test {
            assertEquals(defaultUserSettings, awaitItem())
            ThemeMode.entries.forEach { themeMode ->
                userSettings.saveThemeMode(themeMode)
                assertEquals(defaultUserSettings.copy(themeMode = themeMode), awaitItem())
            }
            cancelAndIgnoreRemainingEvents()
        }
    }
}
