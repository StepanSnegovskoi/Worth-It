package com.metes.worthit.app.util

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.core.util.Consumer
import com.metes.worthit.core.designsystem.theme.PrimaryThemeColor
import com.metes.worthit.core.domain.entity.ThemeColor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged

internal val Configuration.isSystemInDarkTheme
    get() = (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

internal fun ComponentActivity.isSystemInDarkTheme() = callbackFlow {
    channel.trySend(resources.configuration.isSystemInDarkTheme)

    val listener = Consumer<Configuration> {
        channel.trySend(it.isSystemInDarkTheme)
    }

    addOnConfigurationChangedListener(listener)

    awaitClose { removeOnConfigurationChangedListener(listener) }
}
    .distinctUntilChanged()
    .conflate()

fun ThemeColor.toPrimaryThemeColor(): PrimaryThemeColor = when (this) {
    ThemeColor.BLUE -> PrimaryThemeColor.BLUE
    ThemeColor.PINK -> PrimaryThemeColor.PINK
}
