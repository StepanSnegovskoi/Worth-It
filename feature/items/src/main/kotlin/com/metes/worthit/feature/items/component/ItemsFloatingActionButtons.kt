package com.metes.worthit.feature.items.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metes.worthit.feature.items.R
import com.metes.worthit.core.designsystem.R as DesignR
import com.metes.worthit.core.designsystem.component.animation.WorthItAnimatedVisibility
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.theme.AppTheme

internal val bottomButtonSize = 48.dp
internal val bottomButtonSpaceAround = 16.dp
internal val scrollableBottomButtonClearance = bottomButtonSize + bottomButtonSpaceAround * 2

@Composable
internal fun ItemsFloatingActionButtons(
    visible: Boolean,
    selectedItemIds: Set<Int>,
    modifier: Modifier = Modifier,
    onItemsDeleteClick: (Set<Int>) -> Unit,
    onUnselectItemsClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        WorthItAnimatedVisibility(visible = visible) {
            WorthItIconButton(
                modifier = Modifier.size(bottomButtonSize),
                onClick = {
                    onItemsDeleteClick(selectedItemIds)
                }
            ) {
                WorthItIcon(
                    drawableRes = DesignR.drawable.delete_48dp,
                    contentDescriptionRes = R.string.cd_delete_selected_items,
                    tint = AppTheme.colorScheme.primary,
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        WorthItAnimatedVisibility(visible = visible) {
            WorthItIconButton(
                modifier = Modifier.size(bottomButtonSize),
                onClick = {
                    onUnselectItemsClick()
                },
            ) {
                WorthItIcon(
                    drawableRes = R.drawable.hand_off_48dp,
                    contentDescriptionRes = R.string.cd_unselect_items,
                    tint = AppTheme.colorScheme.primary,
                )
            }
        }
    }
}
