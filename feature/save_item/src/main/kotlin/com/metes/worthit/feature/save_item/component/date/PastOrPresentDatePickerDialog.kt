package com.metes.worthit.feature.save_item.component.date

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastOrPresentDatePickerDialog(
    show: Boolean,
    currentDate: LocalDate,
    selectedDateMillis: Long?,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onButtonClick: (Long?) -> Unit,
) {
    val (todayUtcMidnightMillis, currentYear) = remember(currentDate) {
        val todayUtcMidnight = currentDate
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()

        todayUtcMidnight to currentDate.year
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
        initialSelectedDateMillis = selectedDateMillis ?: todayUtcMidnightMillis
    )

    DatePickerDialog(
        show = show,
        state = state,
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        onButtonClick = onButtonClick,
    )
}
