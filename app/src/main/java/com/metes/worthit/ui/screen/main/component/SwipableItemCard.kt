package com.metes.worthit.ui.screen.main.component

import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metes.worthit.domain.entity.Item
import com.metes.worthit.ui.screen.main.ItemUiModel
import java.time.LocalDate

@Composable
fun SwipeableItemCard(
    item: ItemUiModel,
    currentDate: LocalDate,
    modifier: Modifier = Modifier,
    state: SwipeToDismissBoxState = rememberSwipeToDismissBoxState(),
    onClick: (ItemUiModel) -> Unit,
    onDismiss: (Int, String?) -> Unit,
    backgroundContent: @Composable (() -> Unit)? = null
) {
    SwipeToDismissBox(
        modifier = modifier,
        state = state,
        backgroundContent = {
            backgroundContent?.invoke()
        },
        onDismiss = {
            onDismiss(item.id, item.localImagePath)
        }
    ) {
        ItemCard(
            item = item,
            onClick = onClick,
            currentDate = currentDate
        )
    }
}
