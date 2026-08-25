package com.metes.worthit.feature.settings.component.time_unit

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.input.KeyboardType
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.component.other.WorthItTextField
import com.metes.worthit.core.domain.entity.TimeUnit
import com.metes.worthit.core.presentation.namePluralRes
import com.metes.worthit.core.presentation.pricePerPluralRes

@Composable
internal fun PricePerTimeUnitField(
    price: String,
    timeUnit: TimeUnit,
    daysFromPurchase: Int,
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
            WorthItText(text = pluralStringResource(timeUnit.namePluralRes, daysFromPurchase, daysFromPurchase))
        },
    )
}
