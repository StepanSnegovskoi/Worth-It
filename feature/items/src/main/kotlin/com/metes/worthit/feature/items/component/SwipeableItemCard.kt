package com.metes.worthit.feature.items.component

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.metes.worthit.feature.items.ItemUiModel
import com.metes.worthit.feature.items.R
import kotlin.math.roundToInt

private enum class State {
    CLOSED, OPEN
}

@Composable
fun SwipeableItemCard(
    item: ItemUiModel,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    onDeleteClick: (Int, String?) -> Unit,
) {
    val density = LocalDensity.current

    val state = remember {
        AnchoredDraggableState(
            initialValue = State.CLOSED,
            anchors = DraggableAnchors {
                State.CLOSED at 0f
                State.OPEN at with(density) { 96.dp.toPx() }
            }
        )
    }

    Box(
        modifier = modifier,
    ) {
        IconButton(
            modifier = Modifier
                .size(96.dp)
                .padding(8.dp)
                .align(Alignment.CenterStart),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                onDeleteClick(item.id, item.localImagePath)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.delete_48dp),
                contentDescription = stringResource(R.string.cd_delete)
            )
        }
        ItemCard(
            item = item,
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
        )
    }
}
