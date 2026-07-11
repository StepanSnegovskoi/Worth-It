package com.metes.worthit.ui.screen.add_item.component.date

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.metes.worthit.R
import com.metes.worthit.ui.component.WorthItTextField

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
                    painter = painterResource(R.drawable.calendar_24dp),
                    contentDescription = null
                )
            }
        }
    )
}