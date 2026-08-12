package com.metes.worthit.feature.items.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metes.worthit.feature.items.ItemUiModel

@Composable
fun Items(
    items: List<ItemUiModel>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onClick: (Int) -> Unit,
    onDeleteClick: (Int, String?) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = items, key = { it.id }) { item ->
            SwipeableItemCard(
                modifier = Modifier.animateItem(),
                item = item,
                onClick = onClick,
                onDeleteClick = onDeleteClick,
            )
        }
    }
}
