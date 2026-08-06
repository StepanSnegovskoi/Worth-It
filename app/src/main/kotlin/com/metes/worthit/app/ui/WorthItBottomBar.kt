package com.metes.worthit.app.ui

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.metes.worthit.core.designsystem.component.nav.WorthItBottomBarItem
import com.metes.worthit.core.navigation.Screen

@Composable
fun WorthItBottomBar(
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
    onNavigate: (Screen) -> Unit,
) {
    NavigationBar(modifier = modifier) {
        bottomNavItems.forEach { navItem ->
            val isSelected = currentDestination?.hasRoute(navItem.routeClass) == true

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
