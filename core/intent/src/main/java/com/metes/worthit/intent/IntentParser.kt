package com.metes.worthit.intent

import android.content.Intent
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntentParser @Inject constructor(
    private val processors: Map<String, @JvmSuppressWildcards IntentProcessor>,
) {
    fun parse(intent: Intent?): AppIntentEvent {
        if (intent == null) return AppIntentEvent.Ignored
        return processors[intent.action]?.extractEvent(intent) ?: AppIntentEvent.Ignored
    }
}
