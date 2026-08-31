package com.metes.worthit.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.domain.entity.UserPreferences
import com.metes.worthit.core.domain.utils.UserSettings
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.intent.AppIntentEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.uuid.Uuid

@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    userSettings: UserSettings,
) : ViewModel() {

    val uiState = userSettings.preferences
        .map { userPreferences ->
            MainActivityUiState(
                userPreferences = userPreferences,
                isLoading = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainActivityUiState()
        )

    fun processEvent(event: AppIntentEvent) {
        when (event) {
            AppIntentEvent.Ignored -> return

            is AppIntentEvent.Image -> {
                navigationManager.navigateTo(Screen.SaveItem(imagePath = event.imageUri, sessionId = Uuid.random().toString()))
            }
        }
    }
}

data class MainActivityUiState(
    val userPreferences: UserPreferences = UserPreferences(
        currency = Currency.fromNameOrDefault(null),
        themeColor = ThemeColor.fromNameOrDefault(null),
        themeMode = ThemeMode.fromNameOrDefault(null)
    ),
    val isLoading: Boolean = true
)
