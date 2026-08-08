package com.metes.worthit.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.metes.worthit.core.navigation.NavigationEvent
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.core.navigation.safeNavigateTo
import com.metes.worthit.core.navigation.safePopBackStack

@Composable
fun GlobalNavigationEffect(
    navController: NavHostController,
    navigationManager: NavigationManager
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            navigationManager.navEvents.collect { event ->
                when (event) {
                    is NavigationEvent.NavigateTo -> {
                        navController.safeNavigateTo(event.screen)
                    }

                    NavigationEvent.NavigateBack -> navController.safePopBackStack()
                }
            }
        }
    }
}