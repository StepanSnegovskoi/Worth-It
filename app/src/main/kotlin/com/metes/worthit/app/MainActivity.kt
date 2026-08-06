package com.metes.worthit.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.metes.worthit.core.designsystem.theme.WorthItTheme
import com.metes.worthit.app.ui.AppNavigation
import com.metes.worthit.app.ui.GlobalNavigationEffect
import com.metes.worthit.core.navigation.NavigationEvent
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.core.navigation.safeNavigateTo
import com.metes.worthit.core.navigation.safePopBackStack
import com.metes.worthit.intent.IntentHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var intentHandler: IntentHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

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
        if (currentIntent == null || currentIntent.action == Intent.ACTION_MAIN) return

        intentHandler.handle(currentIntent)
        intent = Intent()
    }
}
