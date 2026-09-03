package com.metes.worthit.feature.settings.component.date

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.input.WorthItTextField
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.designsystem.util.rememberDateFormatter
import com.metes.worthit.feature.save_item.R
import java.time.LocalDate

@Composable
internal fun DateField(
    date: String,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
) {
    WorthItTextField(
        modifier = modifier,
        value = date,
        readOnly = true,
        // onValueChange not supported
        onValueChange = {},
        label = { Text(text = stringResource(R.string.date_hint)) },
        trailingIcon = {
            WorthItIconButton(
                onClick = onIconClick
            ) {
                WorthItIcon(
                    drawableRes = R.drawable.calendar_24dp,
                    contentDescriptionRes = R.string.cd_select_date,
                )
            }
        }
    )
}

@Preview
@Composable
private fun DateFieldPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        val dateFormatter = rememberDateFormatter()
        Surface(color = AppTheme.colorScheme.background) {
            DateField(
                date = dateFormatter.format(LocalDate.now()),
                modifier = Modifier.padding(8.dp),
                onIconClick = { },
            )
        }
    }
}
