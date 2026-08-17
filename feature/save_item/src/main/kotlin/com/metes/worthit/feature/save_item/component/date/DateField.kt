package com.metes.worthit.feature.save_item.component.date

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItIconButton
import com.metes.worthit.core.designsystem.component.other.WorthItOutlinedTextField
import com.metes.worthit.feature.save_item.R

@Composable
fun DateField(
    date: String,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
) {
    WorthItOutlinedTextField(
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
