package com.metes.worthit.feature.settings.component.date

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.metes.worthit.core.common.toUtcEpochMilli
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PastOrPresentDatePickerDialog(
    show: Boolean,
    currentDate: LocalDate,
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
) {
    val (todayUtcMidnightMillis, currentYear) = remember(currentDate) {
        val todayUtcMidnight = currentDate.toUtcEpochMilli()

        todayUtcMidnight to currentDate.year
    }

    val selectedDateUtcMillis = remember(selectedDate) {
        selectedDate.toUtcEpochMilli()
    }

    val selectableDates = remember(currentDate) {
        object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= todayUtcMidnightMillis
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year <= currentYear
            }
        }
    }

    val state = rememberDatePickerState(
        selectableDates = selectableDates,
        initialSelectedDateMillis = selectedDateUtcMillis
    )

    DatePickerDialog(
        show = show,
        state = state,
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        onDateSelected = onDateSelected,
    )
}
