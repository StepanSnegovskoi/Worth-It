package com.metes.worthit.ui.navigation

import android.net.Uri
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.metes.worthit.ui.screen.add_item.AddItemRoute
import com.metes.worthit.ui.screen.add_item.AddItemViewModel
import com.metes.worthit.ui.screen.main.ItemsRoute

@Composable
fun AppNavigation(
    sharedUri: Uri?,
    onSharedUriConsumed: () -> Unit,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val currentBackStack by navHostController.currentBackStackEntryFlow.collectAsStateWithLifecycle(null)

    LaunchedEffect(sharedUri, currentBackStack) {
        if (sharedUri != null && currentBackStack != null) {
            navHostController.navigateToAddItem(sharedUri.toString()) {
                launchSingleTop = true
            }
            onSharedUriConsumed()
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
                onNavigateToAddItem = navHostController::navigateToAddItem
            )
        }

        composable<Screen.AddItem> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.AddItem>()
            val uriString = route.imageUriString
            val uri = uriString?.toUri()

            AddItemRoute(
                imageUri = uri,
                onNavigateToItems = navHostController::navigateBack,
                onBackClick = navHostController::navigateBack
            )
        }
    }
}
