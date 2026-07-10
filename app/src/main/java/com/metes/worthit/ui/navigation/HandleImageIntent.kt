package com.metes.worthit.ui.navigation

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.core.util.Consumer
import com.metes.worthit.ui.utils.getImageUriOrNull

// need to avoid navigation-compose deeplinks, because it was not working
@Composable
fun HandleImageIntent(
    onImageShared: (Uri) -> Unit
) {
    val activity = LocalActivity.current as ComponentActivity

    DisposableEffect(activity) {
        val listener = Consumer<Intent> { intent ->
            if (intent.action == Intent.ACTION_SEND && intent.type?.startsWith("image/") == true) {
                val uri = intent.getImageUriOrNull()

                if (uri != null) {
                    onImageShared(uri)
                    // needs to be cleared because when user change configuration
                    // it works again
                    intent.action = Intent.ACTION_MAIN
                    intent.removeExtra(Intent.EXTRA_STREAM)
                }
            }
        }

        activity.addOnNewIntentListener(listener)
        listener.accept(activity.intent)

        onDispose {
            activity.removeOnNewIntentListener(listener)
        }
    }
}