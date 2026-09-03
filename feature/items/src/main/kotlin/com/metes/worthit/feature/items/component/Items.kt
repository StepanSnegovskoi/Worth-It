package com.metes.worthit.feature.items.component

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.items.ItemUiModel
import java.time.LocalDate

@Composable
internal fun Items(
    items: List<ItemUiModel>,
    selectedItemIds: Set<Int>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit,
    onDeleteClick: (Int, String?) -> Unit,
    contentIfEmpty: @Composable (() -> Unit)? = null
) {
    val density = LocalDensity.current

    if (items.isEmpty()) {
        contentIfEmpty?.invoke()
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = items,
                key = { it.id },
                contentType = { "ItemCard" },
            ) { item ->
                SwipeableItemCard(
                    item = item,
                    isSelected = item.id in selectedItemIds,
                    modifier = Modifier.animateItem(),
                    state = remember(density) {
                        AnchoredDraggableState(
                            initialValue = State.CLOSED,
                            anchors = DraggableAnchors {
                                State.CLOSED at 0f
                                State.OPEN at with(density) { 96.dp.toPx() }
                            }
                        )
                    },
                    onClick = onClick,
                    onLongClick = onLongClick,
                    onDeleteClick = onDeleteClick,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ItemsPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            Items(
                items = buildList {
                    repeat(5) {
                        val item = ItemUiModel(
                            id = it,
                            name = "Car",
                            localImagePath = null,
                            dateOfPurchase = LocalDate.now(),
                        )
                        add(item)
                    }
                },
                selectedItemIds = setOf(2, 4),
                contentPadding = PaddingValues(8.dp),
                onClick = { },
                onLongClick = { },
                onDeleteClick = { _, _ -> },
            )
        }
    }
}
