package com.metes.worthit.app.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.metes.worthit.R
import com.metes.worthit.core.navigation.Screen
import kotlin.reflect.KClass

data class BottomNavItem(
    val route: Screen,
    val routeClass: KClass<out Any>,
    @param:StringRes val titleResId: Int,
    @param:DrawableRes val iconResId: Int,
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Screen.Items,
        routeClass = Screen.Items::class,
        titleResId = R.string.items_screen,
        iconResId = R.drawable.items_24dp,
    ),
    BottomNavItem(
        route = Screen.SaveItem(),
        routeClass = Screen.SaveItem::class,
        titleResId = R.string.save_item_screen,
        iconResId = R.drawable.edit_24dp,
    ),
    BottomNavItem(
        route = Screen.Settings,
        routeClass = Screen.Settings::class,
        titleResId = R.string.settings_screen,
        iconResId = R.drawable.settings_24dp,
    ),
)
