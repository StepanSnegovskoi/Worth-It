package com.metes.worthit.ui.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Serializable

const val SCREEN_ITEMS_NAME = "Items"
const val SCREEN_ADD_ITEM_NAME = "AddItem"

@Serializable
sealed class Screen(val name: String) {

    @Serializable
    data object Items : Screen(SCREEN_ITEMS_NAME) {}

    @Serializable
    data class AddItem(val imageUriString: String? = null) : Screen(SCREEN_ADD_ITEM_NAME)
}

fun NavHostController.navigateToAddItem(imageUri: String? = null, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(Screen.AddItem(imageUri), builder)
}

fun NavHostController.navigateBack() {
    popBackStack()
}