package com.metes.worthit.feature.settings.component.input_field

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.component.input.WorthItTextField
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.presentation.UiText
import com.metes.worthit.core.presentation.iconResId
import com.metes.worthit.feature.save_item.R

@Composable
internal fun PriceField(
    price: String,
    currency: Currency,
    priceError: UiText?,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
    onPriceChange: (String) -> Unit,
) {
    WorthItTextField(
        modifier = modifier,
        value = price,
        onValueChange = onPriceChange,
        errorMessage = priceError?.asString(),
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
