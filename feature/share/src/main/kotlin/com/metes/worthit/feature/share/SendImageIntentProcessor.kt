package com.metes.worthit.feature.share

import android.content.Intent
import com.metes.worthit.intent.AppIntentEvent
import com.metes.worthit.intent.IntentProcessor
import com.metes.worthit.intent.getImageUriOrNull
import javax.inject.Inject

class SendImageIntentProcessor @Inject constructor() : IntentProcessor {

    override fun extractEvent(intent: Intent): AppIntentEvent {
        val type = intent.type ?: return AppIntentEvent.Ignored

        return if (type.startsWith("image/")) {
            val uri = intent.getImageUriOrNull()
            if (uri != null) {
                AppIntentEvent.Image(imageUri = uri.toString())
            } else {
                AppIntentEvent.Ignored
            }
        } else {
            AppIntentEvent.Ignored
        }
    }
}
