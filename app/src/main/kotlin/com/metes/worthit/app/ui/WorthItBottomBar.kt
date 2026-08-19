package com.metes.worthit.app.ui

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation3.runtime.NavBackStack
import com.metes.worthit.core.designsystem.component.nav.WorthItBottomBarItem
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.navigation.Screen

@Composable
fun WorthItBottomBar(
    currentScreen: Screen,
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = AppTheme.colorScheme.background
    ) {
        items.forEach { navItem ->
            val isSelected = when (navItem.route) {
                is Screen.Items -> currentScreen is Screen.Items
                is Screen.SaveItem -> currentScreen is Screen.SaveItem
                is Screen.Settings -> currentScreen is Screen.Settings
            }

            WorthItBottomBarItem(
                selected = isSelected,
                titleRes = navItem.titleResId,
                iconRes = navItem.iconResId,
                onClick = {
                    if (!isSelected) {
                        onNavigate(navItem.route)
                    }
                }
            )
        }
    }
}
