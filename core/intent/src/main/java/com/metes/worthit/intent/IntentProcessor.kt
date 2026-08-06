package com.metes.worthit.intent

import android.content.Intent

interface IntentProcessor {
    fun process(intent: Intent)
}