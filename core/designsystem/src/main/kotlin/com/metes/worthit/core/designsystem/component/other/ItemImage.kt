package com.metes.worthit.core.designsystem.component.other

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun ItemImage(
    @DrawableRes defaultImageDrawableRes: Int,
    contentDescription: String?,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    model: Any? = null,
) {
    if (model != null) {
        AsyncImage(
            modifier = modifier,
            model = model,
            contentDescription = contentDescription,
            contentScale = contentScale,
        )
    } else {
        Image(
            modifier = modifier,
            painter = painterResource(defaultImageDrawableRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = AppTheme.colorScheme.primary),
        )
    }
}
