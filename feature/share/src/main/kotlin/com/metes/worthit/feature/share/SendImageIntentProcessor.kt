package com.metes.worthit.feature.share

import android.content.Intent
import com.metes.worthit.core.navigation.NavigationManager
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.intent.IntentProcessor
import com.metes.worthit.intent.getImageUriOrNull
import javax.inject.Inject

class SendImageIntentProcessor @Inject constructor(
    private val navigationManager: NavigationManager
) : IntentProcessor {

    override fun process(intent: Intent) {
        if (intent.type?.startsWith("image/") == true) {
            val uri = intent.getImageUriOrNull()
            if (uri != null) {
                navigationManager.navigateTo(Screen.SaveItem(imagePath = uri.toString()))
            }
        }
    }
}