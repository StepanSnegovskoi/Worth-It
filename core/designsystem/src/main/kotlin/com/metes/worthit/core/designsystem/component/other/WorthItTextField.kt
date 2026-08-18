package com.metes.worthit.core.designsystem.component.other

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.metes.worthit.core.designsystem.component.defaults.WorthItTextFieldDefaults
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItTextField(
    value: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    colors: TextFieldColors = WorthItTextFieldDefaults.colors(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        textStyle = textStyle,
        maxLines = maxLines,
        readOnly = readOnly,
        singleLine = singleLine,
        isError = isError,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        colors = colors,
        label = label,
        placeholder = placeholder,
        trailingIcon = trailingIcon,
        supportingText = {
            if (isError && errorMessage != null) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    )
}
