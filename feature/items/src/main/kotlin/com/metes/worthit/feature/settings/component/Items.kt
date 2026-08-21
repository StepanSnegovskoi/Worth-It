package com.metes.worthit.feature.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.settings.ItemUiModel

@Composable
fun Items(
    items: List<ItemUiModel>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    onEmptyListClick: () -> Unit,
    onDeleteClick: (Int, String?) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (items.isEmpty()) {
            item(
                key = "EmptyList",
                contentType = { "EmptyList" }
            ) {
                Box(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(AppTheme.shape.container),
                    contentAlignment = Alignment.Center,
                ) {
                    ItemsListIsEmpty(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = onEmptyListClick,
                    )
                }
            }
        }
        items(
            items = items,
            key = { it.id },
            contentType = { "ItemCard" },
        ) { item ->
            SwipeableItemCard(
                modifier = Modifier.animateItem(),
                item = item,
                onClick = onClick,
                onDeleteClick = onDeleteClick,
            )
        }
    }
}
