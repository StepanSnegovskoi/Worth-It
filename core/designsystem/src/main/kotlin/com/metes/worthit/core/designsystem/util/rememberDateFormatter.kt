package com.metes.worthit.core.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun rememberDateFormatter(
    formatStyle: FormatStyle = FormatStyle.MEDIUM,
    zoneOffset: ZoneOffset = ZoneOffset.UTC,
): DateTimeFormatter {
    val configuration = LocalConfiguration.current

    return remember(configuration.locales) {
        DateTimeFormatter.ofLocalizedDate(formatStyle)
            .withZone(zoneOffset)
    }
}
