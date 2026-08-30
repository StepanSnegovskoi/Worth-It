package com.metes.worthit.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.domain.entity.UserPreferences
import com.metes.worthit.core.domain.utils.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userSettings: UserSettings,
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = userSettings.preferences.map { preferences ->
        SettingsUiState.Success(preferences = preferences,)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingsUiState.Loading
    )

    fun processCommand(command: SettingsCommand) {
        when (command) {
            is SettingsCommand.SelectThemeColor -> {
                viewModelScope.launch {
                    userSettings.saveThemeColor(command.themeColor)
                }
            }

            is SettingsCommand.SelectThemeMode -> {
                viewModelScope.launch {
                    userSettings.saveThemeMode(command.themeMode)
                }
            }
        }
    }
}

sealed interface SettingsUiState {
    data class Success(
        val preferences: UserPreferences = UserPreferences(
            currency = Currency.fromNameOrDefault(null),
            themeColor = ThemeColor.fromNameOrDefault(null),
            themeMode = ThemeMode.fromNameOrDefault(null),
        )
    ) : SettingsUiState

    data object Loading : SettingsUiState
}

sealed interface SettingsCommand {
    data class SelectThemeColor(val themeColor: ThemeColor) : SettingsCommand
    data class SelectThemeMode(val themeMode: ThemeMode) : SettingsCommand
}
