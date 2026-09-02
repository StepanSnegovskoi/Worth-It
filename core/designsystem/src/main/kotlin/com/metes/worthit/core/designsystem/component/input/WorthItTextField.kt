package com.metes.worthit.core.designsystem.component.input

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.defaults.WorthItTextFieldDefaults
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.preview.BackgroundForPreview
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    errorMessage: String? = null,
    colors: TextFieldColors = WorthItTextFieldDefaults.colors(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = modifier,
        value = value,
        textStyle = textStyle,
        maxLines = maxLines,
        readOnly = readOnly,
        singleLine = singleLine,
        isError = errorMessage != null,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        colors = colors,
        label = label,
        placeholder = placeholder,
        trailingIcon = trailingIcon,
        supportingText = {
            if (errorMessage != null) {
                WorthItText(text = errorMessage, color = AppTheme.colorScheme.error)
            }
        }
    )
}

@Preview(name = "Base")
@Composable
fun WorthItTextFieldPreview() {
    BackgroundForPreview(
        transparent = false
    ) {
        WorthItTextField(
            label = {
                WorthItText(text = "Label")
            },
            value = "Hello World",
            colors = WorthItTextFieldDefaults.colors(),
            onValueChange = {}
        )
    }
}

@Preview(name = "Error")
@Composable
fun WorthItTextFieldPreviewError() {
    BackgroundForPreview(
        transparent = false
    ) {
        WorthItTextField(
            value = "Error",
            colors = WorthItTextFieldDefaults.colors(),
            errorMessage = "Error message",
            onValueChange = {}
        )
    }
}

@Preview(name = "Placeholder")
@Composable
fun WorthItTextFieldPreviewPlaceholder() {
    BackgroundForPreview(
        transparent = false
    ) {
        WorthItTextField(
            value = "",
            placeholder = {
                WorthItText(text = "Placeholder")
            },
            colors = WorthItTextFieldDefaults.colors(),
            onValueChange = {}
        )
    }
}

@Preview(name = "TrailingIcon")
@Composable
fun WorthItTextFieldPreviewTrailingIcon() {
    BackgroundForPreview(
        transparent = false
    ) {
        WorthItTextField(
            value = "TrailingIcon",
            colors = WorthItTextFieldDefaults.colors(),
            trailingIcon = {
                WorthItIcon(
                    drawableRes = R.drawable.close_24dp
                )
            },
            onValueChange = {}
        )
    }
}
