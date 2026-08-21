package com.metes.worthit.feature.settings.component.other

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
internal fun WorthItSnackbar(
    snackbarData: SnackbarData,
    isError: Boolean,
    modifier: Modifier = Modifier,
    @DrawableRes correctIconRes: Int = DesignR.drawable.done_24dp,
    @DrawableRes errorIconRes: Int = DesignR.drawable.error_24dp,
) {
    Snackbar(
        modifier = modifier,
        containerColor = AppTheme.colorScheme.surface,
        contentColor = AppTheme.colorScheme.onSurface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            val iconRes = if (isError) errorIconRes else correctIconRes
            val color = if (isError) AppTheme.colorScheme.error else AppTheme.colorScheme.correct

            WorthItIcon(
                drawableRes = iconRes,
                tint = color
            )
            Spacer(modifier = Modifier.width(12.dp))
            WorthItText(text = snackbarData.visuals.message, maxLines = Int.MAX_VALUE)
        }
    }
}
