package com.metes.worthit.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

@Serializable
sealed interface Screen : NavKey {

    @Serializable
    data object Items : Screen

    @Serializable
    data class SaveItem(
        val itemId: Int? = null,
        val imagePath: String? = null,
        val sessionId: String,
    ) : Screen

    @Serializable
    data object Settings : Screen
}

@Composable
fun rememberMyAppNavBackStack(vararg elements: Screen): NavBackStack<Screen> {
    return rememberSerializable(serializer = serializer()) {
        NavBackStack(*elements)
    }
}

fun NavBackStack<Screen>.safeNavigateBack() {
    if (this.size > 1) {
        removeLastOrNull()
    }
}
