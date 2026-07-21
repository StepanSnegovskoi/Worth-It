package com.metes.worthit.ui.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object Items : Screen {}

    @Serializable
    data class AddItem(val imageUriString: String? = null) : Screen

    @Serializable
    data object Settings : Screen
}

fun NavHostController.navigateBack() {
    safePopBackStack()
}

fun NavHostController.safePopBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}

fun NavHostController.safeNavigateTo(screen: Any, builder: NavOptionsBuilder.() -> Unit = {}) {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navigate(screen, builder)
    }
}