package com.metes.worthit.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.metes.worthit.core.designsystem.theme.WorthItTheme
import com.metes.worthit.app.nav.AppNavigation
import com.metes.worthit.app.utils.getImageUriOrNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val _sharedImageUri = MutableStateFlow<Uri?>(null)
    val sharedImageUri = _sharedImageUri.asStateFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleImageIntent(intent)
        enableEdgeToEdge()
        setContent {
            val pendingUri by sharedImageUri.collectAsStateWithLifecycle()

            WorthItTheme {
                val navController = rememberNavController()

                AppNavigation(
                    modifier = Modifier
                        .fillMaxSize(),
                    sharedUri = pendingUri,
                    navHostController = navController,
                    onSharedUriConsumed = {
                        _sharedImageUri.value = null
                    }
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleImageIntent(intent)
    }

    private fun handleImageIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_SEND && intent.type?.startsWith("image/") == true) {
            val uri = intent.getImageUriOrNull()

            if (uri != null) {
                _sharedImageUri.value = uri
            }

            intent.action = Intent.ACTION_MAIN
            intent.removeExtra(Intent.EXTRA_STREAM)
        }
    }
}
