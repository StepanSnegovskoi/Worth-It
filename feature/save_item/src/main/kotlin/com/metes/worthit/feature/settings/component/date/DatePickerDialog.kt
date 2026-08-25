package com.metes.worthit.feature.settings.component.date

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.common.toLocalDateFromUtc
import com.metes.worthit.core.designsystem.component.defaults.WorthItDatePickerDefaults
import com.metes.worthit.core.designsystem.component.other.WorthItTextButton
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.save_item.R
import java.time.LocalDate

@Composable
internal fun DatePickerDialog(
    show: Boolean,
    state: DatePickerState,
    modifier: Modifier = Modifier,
    colors: DatePickerColors = WorthItDatePickerDefaults.colors(),
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
) {
    if (show) {
        DatePickerDialog(
            modifier = modifier,
            colors = colors,
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
            /**
             * bug in material3.
             *
             * @see androidx.compose.material3.MonthsNavigation
             * @see androidx.compose.material3.YearPickerMenuButton
             *
             * icon in [androidx.compose.material3.YearPickerMenuButton] doesn't use navigationContentColor
             * from 'DatePickerColors', so we need to do this below.
             */
            CompositionLocalProvider(
                LocalContentColor provides colors.navigationContentColor
            ) {
                DatePicker(
                    state = state,
                    showModeToggle = false,
                    colors = WorthItDatePickerDefaults.colors(),
                )
            }
        }
    }
}
