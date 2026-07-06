package com.metes.worthit.ui.navigation

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

private const val ITEMS_SCREEN_NAME = "Items"
private const val ADD_ITEM_SCREEN_NAME = "AddItem"

@Serializable
sealed class Screen(val name: String) {

    @Serializable
    data object Items: Screen(ITEMS_SCREEN_NAME)
    @Serializable
    data object AddItem: Screen(ADD_ITEM_SCREEN_NAME)
}

fun NavHostController.navigateToAddItem() {
    navigate(Screen.AddItem)
}

fun NavHostController.navigateBack() {
    popBackStack()
}