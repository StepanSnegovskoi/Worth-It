package com.metes.worthit.ui.screen.add_item.component

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    imageUri: Uri? = null,
    @DrawableRes defaultImage: Int,
    contentDescription: String?
) {
    if (imageUri != null) {
        AsyncImage(
            modifier = modifier,
            model = imageUri,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            modifier = modifier,
            painter = painterResource(defaultImage),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}