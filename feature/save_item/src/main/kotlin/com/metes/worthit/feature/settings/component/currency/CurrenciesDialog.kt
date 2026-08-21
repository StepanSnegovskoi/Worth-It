package com.metes.worthit.feature.settings.component.currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.domain.entity.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CurrenciesDialog(
    show: Boolean,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onCurrencyClick: (Currency) -> Unit,
) {
    if (show) {
        BasicAlertDialog(
            modifier = modifier
                .clip(AppTheme.shape.container)
                .background(AppTheme.colorScheme.surface),
            onDismissRequest = onDismissRequest,
            content = {
                Currencies(
                    modifier = Modifier
                        .padding(16.dp)
                        .heightIn(max = 320.dp)
                        .wrapContentHeight(),
                    onClick = onCurrencyClick
                )
            }
        )
    }
}
