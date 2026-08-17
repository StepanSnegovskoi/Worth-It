package com.metes.worthit.core.designsystem.component.other

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItOutlinedTextField(
    value: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        textStyle = textStyle,
        maxLines = maxLines,
        readOnly = readOnly,
        singleLine = singleLine,
        isError = isError,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = AppTheme.colorScheme.onBackground,
            unfocusedTextColor = AppTheme.colorScheme.onBackground,
            disabledLabelColor = AppTheme.colorScheme.onBackground,
            focusedLabelColor = AppTheme.colorScheme.onBackground,
            unfocusedLabelColor = AppTheme.colorScheme.onBackground,
        ),
        label = label,
        placeholder = placeholder,
        trailingIcon = trailingIcon,
        supportingText = {
            if (isError && errorMessage != null) {
                Text(text = errorMessage, color = AppTheme.colorScheme.error)
            }
        },
    )
}
