package com.metes.worthit.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.metes.worthit.core.domain.entity.ThemeColor
import com.metes.worthit.core.domain.utils.UserSettings
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.intent.AppIntentEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val userSettings: UserSettings,
) : ViewModel() {

    val uiState = userSettings.preferences
        .map { userPreferences ->
            MainActivityUiState(themeColor = userPreferences.themeColor, isLoading = false)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainActivityUiState()
        )

    fun processEvent(event: AppIntentEvent) {
        when(event) {
            AppIntentEvent.Ignored -> return

            is AppIntentEvent.Image -> {
                navigationManager.navigateTo(Screen.SaveItem(imagePath = event.imageUri))
            }
        }
    }
}

data class MainActivityUiState(
    val themeColor: ThemeColor = ThemeColor.BLUE,
    val isLoading: Boolean = true
)
