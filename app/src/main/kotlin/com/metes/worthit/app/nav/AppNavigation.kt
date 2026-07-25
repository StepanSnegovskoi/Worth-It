package com.metes.worthit.app.nav

import android.net.Uri
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.core.navigation.safeNavigateTo

@Composable
fun AppNavigation(
    sharedUri: Uri?,
    onSharedUriConsumed: () -> Unit,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LaunchedEffect(sharedUri, navBackStackEntry) {
        if (sharedUri != null && navBackStackEntry != null) {
            navHostController.safeNavigateTo(Screen.AddItem(sharedUri.toString()))
            onSharedUriConsumed()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            WorthItBottomBar(
                currentDestination = currentDestination,
                onNavigate = { route ->
                    navHostController.safeNavigateTo(route) {
                        popUpTo(navHostController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { contentPadding ->
        AppNavHost(
            navHostController = navHostController,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .consumeWindowInsets(contentPadding),
        )
    }
}
