package com.metes.worthit.ui.navigation

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.IntentCompat
import androidx.core.net.UriCompat
import androidx.core.os.BundleCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
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

        composable<Screen.AddItem> {
            AddItemRoute(
                modifier = Modifier.fillMaxSize(),
                onNavigateToItems = navHostController::navigateBack,
                onBackClick = navHostController::navigateBack
            )
        }
    }
}