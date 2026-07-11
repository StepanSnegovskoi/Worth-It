package com.metes.worthit.ui.screen.add_item.component.currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.metes.worthit.ui.entity.Currency
import com.metes.worthit.ui.theme.WorthItTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesDialog(
    show: Boolean,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onCurrencyClick: (Currency) -> Unit
) {
    if (show) {
        BasicAlertDialog(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
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