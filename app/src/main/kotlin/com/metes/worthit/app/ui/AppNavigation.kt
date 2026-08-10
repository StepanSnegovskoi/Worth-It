package com.metes.worthit.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.metes.worthit.core.navigation.safeNavigateTo

@Composable
fun AppNavigation(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            WorthItBottomBar(
                currentDestination = currentDestination,
                onNavigate = { route ->
                    navHostController.safeNavigateTo(route) {
                        popUpTo(navHostController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
            )
        }
    ) { contentPadding ->
        AppNavHost(
            navHostController = navHostController,
            scaffoldPadding = contentPadding,
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}
