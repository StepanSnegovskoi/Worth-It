package com.metes.worthit.feature.settings.component.currency

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.dialog.WorthItBasicAlertDialog
import com.metes.worthit.core.domain.entity.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CurrenciesDialog(
    show: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues.Zero,
    onCurrencyClick: (Currency) -> Unit,
) {
    WorthItBasicAlertDialog(
        visible = show,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Currencies(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .heightIn(max = 480.dp)
                .wrapContentHeight(),
            contentPadding = contentPadding,
            onClick = onCurrencyClick,
        )
    }
}
