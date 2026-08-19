package com.metes.worthit.feature.settings.component.date

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItIconButton
import com.metes.worthit.core.designsystem.component.other.WorthItTextField
import com.metes.worthit.feature.save_item.R

@Composable
fun DateField(
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
