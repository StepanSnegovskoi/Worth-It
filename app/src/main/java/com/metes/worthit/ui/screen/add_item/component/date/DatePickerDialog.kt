package com.metes.worthit.ui.screen.add_item.component.date

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.metes.worthit.R

@Composable
fun DatePickerDialog(
    show: Boolean,
    state: DatePickerState,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onButtonClick: () -> Unit
) {
    if (show) {
        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    onClick = onButtonClick
                ) {
                    Text(text = stringResource(R.string.confirm))
                }
            }
        ) {
            DatePicker(state = state, showModeToggle = false)
        }
    }
}