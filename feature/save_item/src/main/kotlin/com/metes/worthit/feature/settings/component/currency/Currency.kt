package com.metes.worthit.feature.settings.component.currency

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.defaults.WorthItCardDefaults
import com.metes.worthit.core.designsystem.component.other.WorthItCard
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.domain.entity.Currency
import com.metes.worthit.core.presentation.iconResId
import com.metes.worthit.core.presentation.titleResId

@Composable
internal fun Currency(
    currency: Currency,
    modifier: Modifier = Modifier,
    onClick: (Currency) -> Unit,
) {
    WorthItCard(
        modifier = modifier,
        onClick = {
            onClick(currency)
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            WorthItText(text = stringResource(currency.titleResId))
            Spacer(Modifier.weight(1f))
            WorthItIcon(
                drawableRes = currency.iconResId,
                contentDescriptionRes = currency.titleResId,
                tint = AppTheme.colorScheme.primary
            )
        }
    }
}
