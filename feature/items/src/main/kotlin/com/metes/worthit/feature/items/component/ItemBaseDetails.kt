package com.metes.worthit.feature.items.component

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.surface.ContentWrapper
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.designsystem.util.rememberDateFormatter
import com.metes.worthit.feature.items.ItemUiModel
import java.time.LocalDate

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
                    .padding(4.dp)
                    .basicMarquee(),
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

@Preview
@Composable
private fun ItemBaseDetailsPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(
            color = AppTheme.colorScheme.surface,
        ) {
            ItemBaseDetails(
                item = ItemUiModel(
                    id = 0,
                    name = "Car",
                    localImagePath = null,
                    dateOfPurchase = LocalDate.now(),
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
