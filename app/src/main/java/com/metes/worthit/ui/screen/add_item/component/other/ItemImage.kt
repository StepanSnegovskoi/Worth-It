package com.metes.worthit.ui.screen.add_item.component.other

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
import com.metes.worthit.R

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    model: Any? = null,
    @DrawableRes defaultImage: Int,
    contentDescription: String?,
    onRemoveClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier.matchParentSize(),
            model = model ?: defaultImage,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )

        if (onRemoveClick != null && model != null) {
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = onRemoveClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.close_48dp),
                    contentDescription = null
                )
            }
        }
    }
}