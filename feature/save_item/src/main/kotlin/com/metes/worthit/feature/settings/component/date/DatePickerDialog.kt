package com.metes.worthit.feature.settings.component.date

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.common.toLocalDateFromUtc
import com.metes.worthit.core.designsystem.component.defaults.WorthItDatePickerDefaults
import com.metes.worthit.core.designsystem.component.other.WorthItTextButton
import com.metes.worthit.feature.save_item.R
import java.time.LocalDate

@Composable
internal fun DatePickerDialog(
    show: Boolean,
    state: DatePickerState,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
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
                        state.selectedDateMillis?.let { millis ->
                            onDateSelected(millis.toLocalDateFromUtc())
                        }
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
