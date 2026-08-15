package com.metes.worthit.core.designsystem.component.other

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.metes.worthit.core.designsystem.R

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    model: Any? = null,
    @DrawableRes defaultImage: Int,
    contentDescription: String?,
    contentScale: ContentScale,
) {
    AsyncImage(
        modifier = modifier,
        model = model ?: defaultImage,
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}
