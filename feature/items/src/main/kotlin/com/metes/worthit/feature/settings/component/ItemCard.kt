package com.metes.worthit.feature.settings.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.defaults.WorthItCardDefaults
import com.metes.worthit.core.designsystem.component.other.ItemImage
import com.metes.worthit.core.designsystem.component.other.WorthItCard
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.items.R
import com.metes.worthit.feature.settings.ItemUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ItemCard(
    item: ItemUiModel,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    onSelectLongClick: (Int) -> Unit,
) {
    val containerColor by animateColorAsState(
        targetValue = if (isSelected) {
            AppTheme.colorScheme.primaryContainer
        } else {
            AppTheme.colorScheme.surface
        },
        animationSpec = tween(200),
        label = "ItemCardContainerColor",
    )

    val iconAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(200),
        label = "ItemCardIconAlpha"
    )

    WorthItCard(
        modifier = modifier
            .fillMaxWidth(),
        colors = WorthItCardDefaults.colors(
            containerColor = containerColor,
            contentColor = AppTheme.colorScheme.onSurface,
        ),
        onClick = {
            onClick(item.id)
        },
        onLongClick = {
            onSelectLongClick(item.id)
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ItemImage(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp)),
                model = item.localImagePath,
                contentScale = ContentScale.Crop,
                defaultImageDrawableRes = R.drawable.image_24dp,
                contentDescription = item.name,
            )
            ItemBaseDetails(
                item = item,
                modifier = Modifier.weight(1f),
            )
            WorthItIcon(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .graphicsLayer {
                        alpha = iconAlpha
                    },
                drawableRes = R.drawable.hand_32dp,
                tint = AppTheme.colorScheme.primary,
            )
        }
    }
}
