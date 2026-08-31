package com.metes.worthit.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation3.runtime.NavBackStack
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.navigation.Screen
import kotlin.uuid.Uuid

@Composable
internal fun AppNavigation(
    backStack: NavBackStack<Screen>,
    bottomNavItems: List<BottomNavItem>,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val currentScreen = backStack.lastOrNull() ?: Screen.Items

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = AppTheme.colorScheme.background,
        bottomBar = {
            WorthItBottomBar(
                currentScreen = currentScreen,
                items = bottomNavItems,
                onNavigate = { tab ->
                    keyboardController?.hide()
                    navigateBottomBar(backStack, tab)
                },
            )
        }
    ) { contentPadding ->
        AppNavDisplay(
            backStack = backStack,
            scaffoldPadding = contentPadding,
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}

private fun navigateBottomBar(backStack: NavBackStack<Screen>, tab: BottomTab) {
    when(tab) {
        BottomTab.Items -> {
            backStack.removeAll { it != Screen.Items }
        }

        BottomTab.SaveItem -> {
            backStack.removeAll { it is Screen.SaveItem }
            backStack.add(Screen.SaveItem(sessionId = Uuid.random().toString()))

        }

        BottomTab.Settings -> {
            backStack.removeAll { it is Screen.Settings }
            backStack.add(Screen.Settings)
        }
    }
}
