package com.metes.worthit.core.designsystem.component.image

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
fun WorthItImage(
    @DrawableRes defaultImageDrawableRes: Int,
    contentDescription: String?,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter = ColorFilter.tint(color = AppTheme.colorScheme.primary),
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
            colorFilter = colorFilter,
        )
    }
}
