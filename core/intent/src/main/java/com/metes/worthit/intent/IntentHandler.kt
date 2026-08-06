package com.metes.worthit.intent

import android.content.Intent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntentHandler @Inject constructor(
    private val processors: Map<String, @JvmSuppressWildcards IntentProcessor>,
) {
    fun handle(intent: Intent?) {
        if (intent == null) return
        val action = intent.action ?: return

        processors[action]?.process(intent)
    }
}
