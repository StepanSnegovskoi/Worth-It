package com.metes.worthit.feature.save_item.component.date

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.designsystem.component.defaults.WorthItDatePickerDefaults
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.component.other.WorthItTextButton
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.save_item.R

@Composable
fun DatePickerDialog(
    show: Boolean,
    state: DatePickerState,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onButtonClick: (Long?) -> Unit,
) {
    if (show) {
        DatePickerDialog(
            modifier = modifier,
            colors = WorthItDatePickerDefaults.colors(),
            onDismissRequest = onDismissRequest,
            confirmButton = {
                WorthItTextButton(
                    text = stringResource(R.string.confirm),
                    onClick = {
                        onButtonClick(state.selectedDateMillis)
                    }
                )
            }
        ) {
            DatePicker(
                state = state,
                showModeToggle = false,
                colors = WorthItDatePickerDefaults.colors(),
            )
        }
    }
}
