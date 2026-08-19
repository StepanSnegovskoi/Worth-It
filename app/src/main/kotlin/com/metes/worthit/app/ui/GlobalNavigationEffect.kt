package com.metes.worthit.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation3.runtime.NavBackStack
import com.metes.worthit.core.navigation.NavigationEvent
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.core.navigation.Screen

@Composable
fun GlobalNavigationEffect(
    backStack: NavBackStack<Screen>,
    navigationManager: NavigationManager,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            navigationManager.navEvents.collect { event ->
                when (event) {
                    is NavigationEvent.NavigateTo -> {
                        backStack.add(event.screen)
                    }
                }
            }
        }
    }
}
