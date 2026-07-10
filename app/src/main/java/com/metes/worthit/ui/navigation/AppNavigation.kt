package com.metes.worthit.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.metes.worthit.ui.screen.add_item.AddItemRoute
import com.metes.worthit.ui.screen.main.ItemsRoute

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    HandleImageIntent { uri ->
        navHostController.navigateToAddItem(uri.toString()) {
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = Screen.Items,
        modifier = modifier,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }
    ) {
        composable<Screen.Items> {
            ItemsRoute(
                modifier = Modifier.fillMaxSize(),
                onNavigateToAddItem = navHostController::navigateToAddItem
            )
        }

        composable<Screen.AddItem> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.AddItem>()
            val uriString = route.imageUriString
            val uri = uriString?.toUri()

            AddItemRoute(
                imageUri = uri,
                modifier = Modifier.fillMaxSize(),
                onNavigateToItems = navHostController::navigateBack,
                onBackClick = navHostController::navigateBack
            )
        }
    }
}
