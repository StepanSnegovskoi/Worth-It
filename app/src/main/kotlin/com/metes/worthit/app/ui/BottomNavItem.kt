package com.metes.worthit.app.ui

import  androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.metes.worthit.R
import com.metes.worthit.core.designsystem.R as DesignR

internal enum class BottomTab {
    Items,
    SaveItem,
    Settings,
}

internal data class BottomNavItem(
    val tab: BottomTab,
    @param:StringRes val titleResId: Int,
    @param:DrawableRes val iconResId: Int,
)

internal val bottomNavItems = listOf(
    BottomNavItem(
        tab = BottomTab.Items,
        titleResId = R.string.items_screen,
        iconResId = DesignR.drawable.items_24dp,
    ),
    BottomNavItem(
        tab = BottomTab.SaveItem,
        titleResId = R.string.save_item_screen,
        iconResId = DesignR.drawable.edit_24dp,
    ),
    BottomNavItem(
        tab = BottomTab.Settings,
        titleResId = R.string.settings_screen,
        iconResId = DesignR.drawable.settings_24dp,
    ),
)
