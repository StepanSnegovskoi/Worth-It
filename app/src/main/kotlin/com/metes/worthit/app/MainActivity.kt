package com.metes.worthit.app

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.metes.worthit.app.ui.AppNavigation
import com.metes.worthit.app.ui.GlobalNavigationEffect
import com.metes.worthit.app.ui.bottomNavItems
import com.metes.worthit.app.util.isSystemInDarkTheme
import com.metes.worthit.app.util.toPrimaryThemeColor
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.domain.entity.ThemeMode
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.core.navigation.rememberMyAppNavBackStack
import com.metes.worthit.intent.IntentParser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var intentParser: IntentParser

    private val mainViewModel: MainActivityViewModel by viewModels()
    private val isSystemInDarkThemeStateFlow by lazy {
        combine(
            mainViewModel.uiState,
            isSystemInDarkTheme()
        ) { uiState, isSystemDarkTheme ->
            when(uiState.userPreferences.themeMode) {
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                ThemeMode.SYSTEM -> isSystemDarkTheme
            }
        }.stateIn(
            scope = lifecycleScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = resources.configuration.isSystemInDarkTheme
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            mainViewModel.uiState.value.isLoading
        }

        processCurrentIntent(intent)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                isSystemInDarkThemeStateFlow.collect { isDarkTheme ->
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
            val isDarkTheme by isSystemInDarkThemeStateFlow.collectAsStateWithLifecycle(
                initialValue = resources.configuration.isSystemInDarkTheme
            )
            val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()
            val backStack = rememberMyAppNavBackStack(Screen.Items)

            GlobalNavigationEffect(
                backStack = backStack,
                navigationManager = navigationManager
            )

            AppTheme(
                isDarkTheme = isDarkTheme,
                primaryThemeColor = uiState.userPreferences.themeColor.toPrimaryThemeColor(),
            ) {
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
            mainViewModel.processEvent(event)

            currentIntent.putExtra(EXTRA_INTENT_HANDLED, true)
            intent = currentIntent
        }
    }

    companion object {
        private const val EXTRA_INTENT_HANDLED = "com.metes.worthit.EXTRA_INTENT_HANDLED"
    }
}
