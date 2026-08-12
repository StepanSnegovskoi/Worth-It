package com.metes.worthit.feature.add_item.component.other

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.metes.worthit.core.designsystem.component.other.WorthItOutlinedTextField
import com.metes.worthit.core.ui.iconResId
import com.metes.worthit.feature.add_item.R
import com.metes.worthit.core.designsystem.component.other.WorthItTextField
import com.metes.worthit.core.domain.entity.Currency

@Composable
fun PriceField(
    price: String,
    currency: Currency,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
    onPriceChange: (String) -> Unit,
) {
    WorthItOutlinedTextField(
        modifier = modifier,
        value = price,
        onValueChange = onPriceChange,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        label = { Text(text = stringResource(R.string.price_hint)) },
        trailingIcon = {
            IconButton(
                onClick = onIconClick
            ) {
                Icon(
                    painter = painterResource(currency.iconResId),
                    contentDescription = null
                )
            }
        }
    )
}
