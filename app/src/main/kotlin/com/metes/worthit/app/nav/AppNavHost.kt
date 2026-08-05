package com.metes.worthit.app.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.core.navigation.navigateBack
import com.metes.worthit.core.navigation.safeNavigateTo
import com.metes.worthit.feature.add_item.SaveItemRoute
import com.metes.worthit.feature.items.ItemsRoute
import com.metes.worthit.feature.items.SettingsScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Items,
        modifier = modifier,
    ) {
        composable<Screen.Items> {
            ItemsRoute(
                onNavigateToSaveItem = { itemId ->
                    navHostController.safeNavigateTo(Screen.SaveItem(itemId = itemId))
                }
            )
        }

        composable<Screen.SaveItem> {
            SaveItemRoute(
                onNavigateToItems = navHostController::navigateBack,
                onBackClick = navHostController::navigateBack
            )
        }

        composable<Screen.Settings> {
            SettingsScreen()
        }
    }
}
