package com.metes.worthit.ui.screen.main.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.metes.worthit.R
import com.metes.worthit.domain.entity.Item
import com.metes.worthit.ui.screen.add_item.component.ItemImage
import kotlinx.coroutines.sync.Mutex

@Composable
fun Item(
    item: Item,
    modifier: Modifier = Modifier
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
                model = item.localPath,
                contentDescription = null,
                defaultImage = R.drawable.image_search_24dp
            )
            Text(text = item.name)
        }
    }
}