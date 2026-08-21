package com.metes.worthit.feature.settings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.other.ContentWrapper
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.util.rememberDateFormatter
import com.metes.worthit.feature.settings.ItemUiModel

@Composable
internal fun ItemBaseDetails(
    item: ItemUiModel,
    modifier: Modifier = Modifier,
) {
    val dateFormatter = rememberDateFormatter()

    val formattedDates = remember(item.dateOfPurchase) {
        dateFormatter.format(item.dateOfPurchase)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ContentWrapper {
            WorthItText(
                modifier = Modifier
                    .padding(4.dp),
                text = item.name
            )
        }
        formattedDates?.let {
            ContentWrapper {
                WorthItText(
                    modifier = Modifier
                        .padding(4.dp),
                    text = formattedDates
                )
            }
        }
    }
}
