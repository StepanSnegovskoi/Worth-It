package com.metes.worthit.ui.screen.add_item.component

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    model: Any? = null,
    @DrawableRes defaultImage: Int,
    contentDescription: String?
) {
    AsyncImage(
        modifier = modifier,
        model = model ?: defaultImage,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop
    )
}