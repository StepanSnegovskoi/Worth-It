package com.metes.worthit.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.metes.worthit.core.designsystem.theme.WorthItTheme
import com.metes.worthit.app.ui.AppNavigation
import com.metes.worthit.app.ui.GlobalNavigationEffect
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.intent.IntentParser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            GlobalNavigationEffect(
                navController = navController,
                navigationManager = navigationManager
            )

            WorthItTheme {
                AppNavigation(
                    modifier = Modifier
                        .fillMaxSize(),
                    navHostController = navController,
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
