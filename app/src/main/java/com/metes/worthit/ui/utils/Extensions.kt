package com.metes.worthit.ui.utils

import android.content.Intent
import android.net.Uri
import androidx.core.content.IntentCompat
import androidx.core.os.BundleCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

fun NavBackStackEntry.getDeepLinkIntentOrNull(): Intent? {
    val bundle = arguments ?: return null
    return BundleCompat.getParcelable(
        bundle,
        NavController.KEY_DEEP_LINK_INTENT,
        Intent::class.java
    )
}

fun Intent.getImageUriOrNull(): Uri? {
    return IntentCompat.getParcelableExtra(
        this,
        Intent.EXTRA_STREAM,
        Uri::class.java
    )
}