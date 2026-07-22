package com.metes.worthit.app.utils

import android.content.Intent
import android.net.Uri
import androidx.core.content.IntentCompat
import androidx.core.os.BundleCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

fun Intent.getImageUriOrNull(): Uri? {
    return IntentCompat.getParcelableExtra(
        this,
        Intent.EXTRA_STREAM,
        Uri::class.java
    )
}
