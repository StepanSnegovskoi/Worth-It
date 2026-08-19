package com.metes.worthit.feature.settings.component.other

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItIconButton
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.component.other.WorthItTextField
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.presentation.iconResId
import com.metes.worthit.feature.save_item.R

@Composable
fun PriceField(
    price: String,
    currency: Currency,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
    onPriceChange: (String) -> Unit,
) {
    WorthItTextField(
        modifier = modifier,
        value = price,
        onValueChange = onPriceChange,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        label = { Text(text = stringResource(R.string.price_hint)) },
        trailingIcon = {
            WorthItIconButton(
                onClick = onIconClick
            ) {
                WorthItIcon(
                    drawableRes = currency.iconResId,
                    contentDescriptionRes = R.string.cd_select_currency
                )
            }
        },
        placeholder = {
            WorthItText(text = stringResource(R.string.placeholder_price))
        },
    )
}
