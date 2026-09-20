@file:OptIn(ExperimentalCoroutinesApi::class)

package com.metes.worthit.feature.settings

import app.cash.turbine.test
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.domain.entity.UserPreferences
import com.metes.worthit.core.domain.utils.UserSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private val defaultUserPreferences = UserPreferences(
    currency = Currency.fromNameOrDefault(null),
    themeColor = ThemeColor.fromNameOrDefault(null),
    themeMode = ThemeMode.fromNameOrDefault(null),
)

private class FakeUserSettings : UserSettings {

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

class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        viewModel = SettingsViewModel(userSettings = FakeUserSettings())
    }

    @Test
    fun `initial state of uiState is Loading`() =
        runTest {
            viewModel.uiState.test {
                assertEquals(SettingsUiState.Loading, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel processed SelectThemeColor command uiState has correct ThemeColor`() =
        runTest {
            viewModel.uiState.test {
                assertEquals(SettingsUiState.Loading, awaitItem())
                assertEquals(SettingsUiState.Success(defaultUserPreferences), awaitItem())

                viewModel.processCommand(SettingsCommand.SelectThemeColor(ThemeColor.PINK))

                assertEquals(
                    SettingsUiState.Success(
                        preferences = defaultUserPreferences.copy(themeColor = ThemeColor.PINK)
                    ), awaitItem()
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel processed SelectThemeMode command uiState has correct ThemeColor`() =
        runTest {
            viewModel.uiState.test {
                assertEquals(SettingsUiState.Loading, awaitItem())
                assertEquals(SettingsUiState.Success(defaultUserPreferences), awaitItem())

                viewModel.processCommand(SettingsCommand.SelectThemeMode(ThemeMode.DARK))

                assertEquals(
                    SettingsUiState.Success(
                        preferences = defaultUserPreferences.copy(themeMode = ThemeMode.DARK)
                    ), awaitItem()
                )

                cancelAndIgnoreRemainingEvents()
            }
        }
}
