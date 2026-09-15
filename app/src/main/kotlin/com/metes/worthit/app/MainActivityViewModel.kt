package com.metes.worthit.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.app.util.toPrimaryThemeColor
import com.metes.worthit.core.designsystem.theme.PrimaryThemeColor
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
            MainUiState.Loaded(userPreferences = userPreferences)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState.Loading
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

sealed interface MainUiState {
    data object Loading: MainUiState

    data class Loaded(
        val userPreferences: UserPreferences,
    ): MainUiState {

        override fun shouldShouldUseDarkTheme(isSystemInDarkTheme: Boolean): Boolean {
            return when(userPreferences.themeMode) {
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                ThemeMode.SYSTEM -> isSystemInDarkTheme
            }
        }

        override fun primaryThemeColor(): PrimaryThemeColor {
            return userPreferences.themeColor.toPrimaryThemeColor()
        }
    }

    fun shouldKeepSplashScreen() = this is Loading

    fun shouldShouldUseDarkTheme(isSystemInDarkTheme: Boolean) = isSystemInDarkTheme

    fun primaryThemeColor() = ThemeColor.fromNameOrDefault(null).toPrimaryThemeColor()
}
