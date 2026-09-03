package com.metes.worthit.feature.settings.component.date

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.metes.worthit.core.common.toUtcEpochMilli
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit

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

@Preview
@Composable
private fun PastOrPresentDatePickerDialogPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppTheme.colorScheme.background,
        ) {
            val date = LocalDate.now()
            PastOrPresentDatePickerDialog(
                show = true,
                currentDate = date,
                selectedDate = date.minus(1, ChronoUnit.DAYS),
                onDismissRequest = { },
                onDateSelected = { },
            )
        }
    }
}
