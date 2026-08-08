package com.metes.worthit.app

import androidx.lifecycle.ViewModel
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.intent.AppIntentEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
) : ViewModel() {
    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    val state = _state.asStateFlow()

    fun processEvent(event: AppIntentEvent) {
        _state.update { MainState.Idle }

        when(event) {
            AppIntentEvent.Ignored -> return

            is AppIntentEvent.Image -> {
                navigationManager.navigateTo(Screen.SaveItem(imagePath = event.imageUri))
            }
        }
    }
}

sealed interface MainState {
    data object Idle : MainState
    data object Loading : MainState
}
