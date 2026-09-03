package com.metes.worthit.feature.settings.component.time_unit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.input.WorthItTextField
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.domain.entity.TimeUnit
import com.metes.worthit.core.presentation.namePluralRes
import com.metes.worthit.core.presentation.pricePerPluralRes

@Composable
internal fun PricePerTimeUnitField(
    price: String,
    timeUnit: TimeUnit,
    timeUnitsFromPurchase: Int,
    modifier: Modifier = Modifier,
) {
    WorthItTextField(
        modifier = modifier,
        value = price,
        onValueChange = { },
        readOnly = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        label = {
            Text(
                text =
                    pluralStringResource(timeUnit.pricePerPluralRes, 1, 1)
            )
        },
        trailingIcon = {
            WorthItText(
                text = pluralStringResource(
                    timeUnit.namePluralRes,
                    timeUnitsFromPurchase,
                    timeUnitsFromPurchase
                )
            )
        },
    )
}

@Preview
@Composable
private fun PricePerTimeUnitFieldPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            Column {
                TimeUnit.entries.forEach { timeUnit ->
                    PricePerTimeUnitField(
                        modifier = Modifier.padding(8.dp),
                        price = "1999",
                        timeUnit = timeUnit,
                        timeUnitsFromPurchase = 24,
                    )
                }
            }
        }
    }
}
