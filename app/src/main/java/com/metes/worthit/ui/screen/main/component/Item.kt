package com.metes.worthit.ui.screen.main.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metes.worthit.R
import com.metes.worthit.domain.entity.Item
import com.metes.worthit.ui.screen.add_item.component.other.ItemImage

@Composable
fun Item(
    item: Item,
    modifier: Modifier = Modifier,
    @DrawableRes defaultImage: Int = R.drawable.image_24dp,
    contentDescription: String? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ItemImage(
                modifier = Modifier.size(128.dp),
                model = item.imageLocalPath,
                contentDescription = contentDescription,
                defaultImage = defaultImage
            )
            Text(text = item.name)
        }
    }
}