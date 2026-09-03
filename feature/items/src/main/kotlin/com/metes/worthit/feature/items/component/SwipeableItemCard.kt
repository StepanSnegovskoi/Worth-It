package com.metes.worthit.feature.items.component

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.designsystem.R as DesignR
import com.metes.worthit.feature.items.R
import com.metes.worthit.feature.items.ItemUiModel
import java.time.LocalDate
import kotlin.math.roundToInt

enum class State {
    CLOSED, OPEN
}

@Composable
internal fun SwipeableItemCard(
    item: ItemUiModel,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    state: AnchoredDraggableState<State>,
    onClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit,
    onDeleteClick: (Int, String?) -> Unit,
) {
    Box(
        modifier = modifier,
    ) {
        WorthItIconButton(
            modifier = Modifier
                .size(96.dp)
                .padding(8.dp)
                .align(Alignment.CenterStart),
            onClick = {
                onDeleteClick(item.id, item.localImagePath)
            },
        ) {
            WorthItIcon(
                drawableRes = DesignR.drawable.delete_48dp,
                contentDescriptionRes = R.string.cd_delete,
            )
        }
        ItemCard(
            item = item,
            isSelected = isSelected,
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = state.requireOffset().roundToInt(),
                        y = 0
                    )
                }
                .anchoredDraggable(
                    state = state,
                    orientation = Orientation.Horizontal
                ),
            onClick = onClick,
            onLongClick = onLongClick,
        )
    }
}

@Preview
@Composable
private fun SwipeableItemCardPreviewClosed(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    val density = LocalDensity.current
    val state = remember(density) {
        AnchoredDraggableState(
            initialValue = State.CLOSED,
            anchors = DraggableAnchors {
                State.CLOSED at 0f
                State.OPEN at with(density) { 96.dp.toPx() }
            }
        )
    }

    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            SwipeableItemCard(
                item = ItemUiModel(
                    id = 0,
                    name = "Car",
                    localImagePath = null,
                    dateOfPurchase = LocalDate.now()
                ),
                isSelected = false,
                modifier = Modifier.padding(8.dp),
                state = state,
                onClick = { },
                onLongClick = { },
                onDeleteClick = { _, _ -> }
            )
        }
    }
}

@Preview
@Composable
private fun SwipeableItemCardPreviewOpen(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    val density = LocalDensity.current
    val state = remember(density) {
        AnchoredDraggableState(
            initialValue = State.OPEN,
            anchors = DraggableAnchors {
                State.CLOSED at 0f
                State.OPEN at with(density) { 96.dp.toPx() }
            }
        )
    }

    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            SwipeableItemCard(
                item = ItemUiModel(
                    id = 0,
                    name = "Car",
                    localImagePath = null,
                    dateOfPurchase = LocalDate.now()
                ),
                isSelected = false,
                modifier = Modifier.padding(8.dp),
                state = state,
                onClick = { },
                onLongClick = { },
                onDeleteClick = { _, _ -> }
            )
        }
    }
}
