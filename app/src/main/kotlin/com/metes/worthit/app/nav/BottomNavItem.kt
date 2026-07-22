package com.metes.worthit.app.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.metes.worthit.R
import com.metes.worthit.core.navigation.Screen
import kotlin.reflect.KClass

data class BottomNavItem(
    val route: Any,
    val routeClass: KClass<out Any>,
    @param:StringRes val titleResIs: Int,
    @param:DrawableRes val iconResIs: Int,
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Screen.Items,
        routeClass = Screen.Items::class,
        titleResIs = R.string.items_screen,
        iconResIs = R.drawable.items_24dp
    ),
    BottomNavItem(
        route = Screen.AddItem(),
        routeClass = Screen.AddItem::class,
        titleResIs = R.string.add_item_screen,
        iconResIs = R.drawable.add_24dp
    ),
    BottomNavItem(
        route = Screen.Settings,
        routeClass = Screen.Settings::class,
        titleResIs = R.string.settings_screen,
        iconResIs = R.drawable.settings_24dp
    ),
)