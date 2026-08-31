package com.metes.worthit.app.ui

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metes.worthit.core.designsystem.component.nav.WorthItBottomBarItem
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.navigation.Screen

@Composable
internal fun WorthItBottomBar(
    currentScreen: Screen,
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    onNavigate: (BottomTab) -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = AppTheme.colorScheme.background
    ) {
        items.forEach { navItem ->
            val isSelected = when (navItem.tab) {
                BottomTab.Items -> currentScreen is Screen.Items
                BottomTab.SaveItem -> currentScreen is Screen.SaveItem
                BottomTab.Settings -> currentScreen is Screen.Settings
            }

            WorthItBottomBarItem(
                selected = isSelected,
                titleRes = navItem.titleResId,
                iconRes = navItem.iconResId,
                onClick = {
                    if (!isSelected) {
                        onNavigate(navItem.tab)
                    }
                }
            )
        }
    }
}
