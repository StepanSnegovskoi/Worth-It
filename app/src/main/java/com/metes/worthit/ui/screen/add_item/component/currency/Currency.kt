package com.metes.worthit.ui.screen.add_item.component.currency

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.metes.worthit.ui.entity.Currency

@Composable
fun Currency(
    currency: Currency,
    modifier: Modifier = Modifier,
    onClick: (Currency) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        onClick = {
            onClick(currency)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(currency.titleRes))
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(currency.iconRes),
                contentDescription = null
            )
        }
    }
}