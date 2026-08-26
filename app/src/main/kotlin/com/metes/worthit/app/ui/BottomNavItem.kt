package com.metes.worthit.app.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.metes.worthit.R
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.core.designsystem.R as DesignR

internal data class BottomNavItem(
    val route: Screen,
    @param:StringRes val titleResId: Int,
    @param:DrawableRes val iconResId: Int,
)

internal val bottomNavItems = listOf(
    BottomNavItem(
        route = Screen.Items,
        titleResId = R.string.items_screen,
        iconResId = DesignR.drawable.items_24dp,
    ),
    BottomNavItem(
        route = Screen.SaveItem(),
        titleResId = R.string.save_item_screen,
        iconResId = DesignR.drawable.edit_24dp,
    ),
    BottomNavItem(
        route = Screen.Settings,
        titleResId = R.string.settings_screen,
        iconResId = DesignR.drawable.settings_24dp,
    ),
)
