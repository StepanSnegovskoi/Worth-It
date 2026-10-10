package com.metes.worthit.intent

import android.content.Intent
import android.net.Uri
import androidx.core.content.IntentCompat

fun Intent.getImageUriOrNull(): Uri? = IntentCompat.getParcelableExtra(
    this,
    Intent.EXTRA_STREAM,
    Uri::class.java,
)
