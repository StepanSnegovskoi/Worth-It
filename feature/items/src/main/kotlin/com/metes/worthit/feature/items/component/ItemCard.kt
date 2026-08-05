package com.metes.worthit.feature.items.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.other.ItemImage
import com.metes.worthit.feature.items.ItemUiModel
import com.metes.worthit.feature.items.R
import java.time.LocalDate
import com.metes.worthit.core.designsystem.R as DesignR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    item: ItemUiModel,
    currentDate: LocalDate,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    Card(
        onClick = {
            onClick(item.id)
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ItemImage(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp)),
                model = item.localImagePath,
                defaultImage = R.drawable.image_24dp,
                contentDescription = item.name
            )
            ItemBaseDetails(
                item = item,
                currentDate = currentDate
            )
        }
    }
}
