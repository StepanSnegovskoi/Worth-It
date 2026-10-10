@file:OptIn(ExperimentalCoroutinesApi::class)

package com.metes.worthit.feature.settings

import app.cash.turbine.test
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.testing.FakeUserSettings
import com.metes.worthit.core.testing.MainDispatcherRule
import com.metes.worthit.core.testing.defaultUserPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        viewModel = SettingsViewModel(userSettings = FakeUserSettings())
    }

    @Test
    fun `initial state of uiState is Loading`() = runTest {
        assertEquals(SettingsUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `when viewModel processed SelectThemeColor command uiState has correct ThemeColor`() =
        runTest {
            viewModel.uiState.test {
                assertEquals(SettingsUiState.Success(defaultUserPreferences), awaitItem())

                viewModel.processCommand(SettingsCommand.SelectThemeColor(ThemeColor.PINK))

                assertEquals(
                    SettingsUiState.Success(
                        preferences = defaultUserPreferences.copy(themeColor = ThemeColor.PINK),
                    ),
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel processed SelectThemeMode command uiState has correct ThemeColor`() =
        runTest {
            viewModel.uiState.test {
                assertEquals(SettingsUiState.Success(defaultUserPreferences), awaitItem())

                viewModel.processCommand(SettingsCommand.SelectThemeMode(ThemeMode.DARK))

                assertEquals(
                    SettingsUiState.Success(
                        preferences = defaultUserPreferences.copy(themeMode = ThemeMode.DARK),
                    ),
                    awaitItem(),
                )

                cancelAndIgnoreRemainingEvents()
            }
        }
}
