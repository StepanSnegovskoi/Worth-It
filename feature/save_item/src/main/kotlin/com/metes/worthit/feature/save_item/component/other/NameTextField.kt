package com.metes.worthit.feature.save_item.component.other

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItIconButton
import com.metes.worthit.core.designsystem.component.other.WorthItOutlinedTextField
import com.metes.worthit.feature.save_item.R
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
fun NameTextField(
    name: String,
    isError: Boolean,
    modifier: Modifier = Modifier,
    onRemoveNameClick: () -> Unit,
    onNameChange: (String) -> Unit,
) {
    WorthItOutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = name,
        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
        onValueChange = onNameChange,
        isError = isError,
        errorMessage = stringResource(R.string.enter_name),
        label = { Text(text = stringResource(R.string.name_hint)) },
        trailingIcon = {
            if (name.isNotEmpty()) {
                WorthItIconButton(
                    onClick = onRemoveNameClick
                ) {
                    WorthItIcon(
                        drawableRes = DesignR.drawable.close_24dp,
                        contentDescriptionRes = R.string.cd_clear_name
                    )
                }
            }
        },
    )
}
