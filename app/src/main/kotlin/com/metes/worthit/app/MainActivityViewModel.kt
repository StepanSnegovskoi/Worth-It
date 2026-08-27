package com.metes.worthit.app

import androidx.lifecycle.ViewModel
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.intent.AppIntentEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
) : ViewModel() {

    fun processEvent(event: AppIntentEvent) {
        when(event) {
            AppIntentEvent.Ignored -> return

            is AppIntentEvent.Image -> {
                navigationManager.navigateTo(Screen.SaveItem(imagePath = event.imageUri))
            }
        }
    }
}
