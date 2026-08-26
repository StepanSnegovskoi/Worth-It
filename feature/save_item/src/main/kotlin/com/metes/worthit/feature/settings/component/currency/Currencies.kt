package com.metes.worthit.feature.settings.component.currency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.domain.entity.Currency

@Composable
internal fun Currencies(
    modifier: Modifier = Modifier,
    currencies: List<Currency> = Currency.entries,
    contentPadding: PaddingValues = PaddingValues.Zero,
    onClick: (Currency) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding
    ) {
        items(items = currencies, key = { it.name }) { currency ->
            Currency(currency = currency, onClick = onClick)
        }
    }
}
