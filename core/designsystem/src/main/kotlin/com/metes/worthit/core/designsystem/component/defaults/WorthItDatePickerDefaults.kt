package com.metes.worthit.core.designsystem.component.defaults

import androidx.compose.material3.DatePickerColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.metes.worthit.core.designsystem.theme.AppTheme

object WorthItDatePickerDefaults {
    @Composable
    fun colors(): DatePickerColors = DatePickerColors(
            containerColor = AppTheme.colorScheme.background,
            titleContentColor = AppTheme.colorScheme.secondary,
            headlineContentColor = AppTheme.colorScheme.onBackground,
            weekdayContentColor = AppTheme.colorScheme.primary,
            subheadContentColor = AppTheme.colorScheme.onBackground,
            navigationContentColor = AppTheme.colorScheme.primary,
            yearContentColor = AppTheme.colorScheme.onBackground,
            disabledYearContentColor = AppTheme.colorScheme.secondary,
            currentYearContentColor = AppTheme.colorScheme.onBackground,
            selectedYearContentColor = AppTheme.colorScheme.onPrimary,
            disabledSelectedYearContentColor = AppTheme.colorScheme.secondary,
            selectedYearContainerColor = AppTheme.colorScheme.primary,
            disabledSelectedYearContainerColor = AppTheme.colorScheme.secondary,
            dayContentColor = AppTheme.colorScheme.onBackground,
            disabledDayContentColor = AppTheme.colorScheme.secondary,
            selectedDayContentColor = AppTheme.colorScheme.onPrimary,
            disabledSelectedDayContentColor = AppTheme.colorScheme.secondary,
            selectedDayContainerColor = AppTheme.colorScheme.primary,
            disabledSelectedDayContainerColor = Color.Transparent,
            todayContentColor = AppTheme.colorScheme.onBackground,
            todayDateBorderColor = AppTheme.colorScheme.onBackground,
            dayInSelectionRangeContainerColor = AppTheme.colorScheme.background,
            dayInSelectionRangeContentColor = AppTheme.colorScheme.onBackground,
            dividerColor = AppTheme.colorScheme.surface,
            dateTextFieldColors = WorthItTextFieldDefaults.colors(),
        )
}
