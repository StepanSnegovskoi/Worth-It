package com.metes.worthit.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.metes.worthit.ui.screen.add_item.AddItemRoute
import com.metes.worthit.ui.screen.main.ItemsRoute

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Items,
        modifier = modifier
    ) {
        composable<Screen.Items> {
            ItemsRoute(
                modifier = Modifier.fillMaxSize(),
                onNavigateToAddItem = navHostController::navigateToAddItem
            )
        }

        composable<Screen.AddItem> {
            AddItemRoute(
                modifier = Modifier.fillMaxSize(),
                onNavigateToItems = navHostController::navigateBack,
                onBackClick = navHostController::navigateBack
            )
        }
    }
}