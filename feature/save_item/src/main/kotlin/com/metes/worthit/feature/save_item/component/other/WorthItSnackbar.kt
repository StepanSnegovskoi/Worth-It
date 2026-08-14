package com.metes.worthit.feature.save_item.component.other

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
fun WorthItSnackbar(
    snackbarData: SnackbarData,
    isError: Boolean,
    modifier: Modifier = Modifier,
    @DrawableRes correctIconRes: Int = DesignR.drawable.done_24dp,
    @DrawableRes errorIconRes: Int = DesignR.drawable.error_24dp,
) {
    Snackbar(
        modifier = modifier,
        containerColor = if (isError) MaterialTheme.colorScheme.errorContainer else Color.Green,
        contentColor = if (isError) MaterialTheme.colorScheme.onErrorContainer else SnackbarDefaults.contentColor
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            val iconRes = if (isError) errorIconRes else correctIconRes

            Icon(
                painter = painterResource(iconRes),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = snackbarData.visuals.message)
        }
    }
}
