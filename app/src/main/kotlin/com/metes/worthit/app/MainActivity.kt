package com.metes.worthit.app

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.rememberNavBackStack
import com.metes.worthit.app.ui.AppNavigation
import com.metes.worthit.app.ui.GlobalNavigationEffect
import com.metes.worthit.app.ui.bottomNavItems
import com.metes.worthit.app.util.isSystemInDarkTheme
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.core.navigation.rememberMyAppNavBackStack
import com.metes.worthit.intent.IntentParser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.serializer
import javax.inject.Inject
import androidx.core.graphics.toColorInt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var intentParser: IntentParser

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { viewModel.state.value is MainState.Loading }

        processCurrentIntent(intent)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                isSystemInDarkTheme().collect { isDarkTheme ->
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(
                            lightScrim = Color.TRANSPARENT,
                            darkScrim = Color.TRANSPARENT,
                        ) { isDarkTheme },
                        navigationBarStyle = SystemBarStyle.auto(
                            lightScrim = Color.TRANSPARENT,
                            darkScrim = Color.TRANSPARENT
                        ) { isDarkTheme }
                    )

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        window.isNavigationBarContrastEnforced = false
                    }
                }
            }
        }

        setContent {
            val isDarkTheme by isSystemInDarkTheme().collectAsStateWithLifecycle(
                initialValue = resources.configuration.isSystemInDarkTheme
            )
            val backStack = rememberMyAppNavBackStack(Screen.Items)

            GlobalNavigationEffect(
                backStack = backStack,
                navigationManager = navigationManager
            )

            AppTheme(isDarkTheme = isDarkTheme) {
                AppNavigation(
                    backStack = backStack,
                    bottomNavItems = bottomNavItems,
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        processCurrentIntent(intent)
    }

    private fun processCurrentIntent(currentIntent: Intent?) {
        currentIntent?.let {
            val isHandled = currentIntent.getBooleanExtra(EXTRA_INTENT_HANDLED, false)
            if (isHandled) return

            val event = intentParser.parse(currentIntent)
            viewModel.processEvent(event)

            currentIntent.putExtra(EXTRA_INTENT_HANDLED, true)
            intent = currentIntent
        }
    }

    companion object {
        private const val EXTRA_INTENT_HANDLED = "com.metes.worthit.EXTRA_INTENT_HANDLED"
    }
}
