package com.metes.worthit.ui.screen.main.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metes.worthit.domain.entity.Item

@Composable
fun Items(
    items: List<Item>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = items, key = { it.id }) { item ->
            Item(item = item)
        }
    }
}