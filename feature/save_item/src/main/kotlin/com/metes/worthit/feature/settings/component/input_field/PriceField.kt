package com.metes.worthit.feature.settings.component.input_field

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.component.input.WorthItTextField
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme
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

@Preview
@Composable
private fun PriceFieldPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            PriceField(
                modifier = Modifier.padding(8.dp),
                price = "1999",
                currency = Currency.EUR,
                priceError = null,
                onIconClick = { },
                onPriceChange = { },
            )
        }
    }
}

@Preview
@Composable
private fun PriceFieldPreviewError(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            PriceField(
                modifier = Modifier.padding(8.dp),
                price = "19..99",
                currency = Currency.EUR,
                priceError = UiText.StringResource(R.string.price_must_be_a_number),
                onIconClick = { },
                onPriceChange = { },
            )
        }
    }
}
