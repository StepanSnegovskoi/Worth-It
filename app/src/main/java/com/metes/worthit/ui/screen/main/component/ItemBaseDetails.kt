package com.metes.worthit.ui.screen.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metes.worthit.domain.entity.Item
import com.metes.worthit.ui.component.ContentWrapper
import com.metes.worthit.ui.component.WorthItText
import com.metes.worthit.ui.screen.main.ItemUiModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ItemBaseDetails(
    item: ItemUiModel,
    modifier: Modifier = Modifier
) {
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
        if (item.formattedDates != null) {
            ContentWrapper {
                WorthItText(
                    modifier = Modifier
                        .padding(4.dp),
                    text = item.formattedDates
                )
            }
        }
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            if (item.daysCountText != null) {
//                ContentWrapper {
//                    WorthItText(
//                        modifier = Modifier
//                            .padding(4.dp),
//                        text = item.daysCountText
//                    )
//                }
//            }
//            if (item.pricePerDayText != null) {
//                ContentWrapper {
//                    WorthItText(
//                        modifier = Modifier
//                            .padding(4.dp),
//                        text = item.pricePerDayText
//                    )
//                }
//            }
//        }
    }
}
