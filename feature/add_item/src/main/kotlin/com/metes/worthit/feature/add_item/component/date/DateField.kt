package com.metes.worthit.feature.add_item.component.date

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.designsystem.component.other.WorthItTextField
import com.metes.worthit.feature.add_item.R
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
fun DateField(
    date: String,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit
) {
    WorthItTextField(
        modifier = modifier,
        value = date,
        readOnly = true,
        // onValueChange not supported
        onValueChange = {},
        label = { Text(text = stringResource(R.string.date_hint)) },
        trailingIcon = {
            IconButton(
                onClick = onIconClick
            ) {
                Icon(
                    painter = painterResource(DesignR.drawable.calendar_24dp),
                    contentDescription = null
                )
            }
        }
    )
}