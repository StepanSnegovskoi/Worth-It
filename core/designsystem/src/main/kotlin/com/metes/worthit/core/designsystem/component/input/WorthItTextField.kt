package com.metes.worthit.core.designsystem.component.input

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.defaults.WorthItTextFieldDefaults
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
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
                WorthItText(
                    text = errorMessage,
                    color = AppTheme.colorScheme.error,
                    maxLines = Int.MAX_VALUE,
                )
            }
        }
    )
}

// The current rendering only supports APIs up to 36.
@Preview(apiLevel = 36)
@Composable
fun WorthItTextFieldPreviewBase(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            WorthItTextField(
                value = "Hello World",
                modifier = Modifier.padding(8.dp),
                onValueChange = { },
            )
        }
    }
}

@Preview(apiLevel = 36)
@Composable
fun WorthItTextFieldPreviewPlaceholder(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            WorthItTextField(
                value = "",
                modifier = Modifier.padding(8.dp),
                placeholder = {
                    WorthItText(text = "Placeholder")
                },
                onValueChange = { },
            )
        }
    }
}

@Preview(apiLevel = 36)
@Composable
fun WorthItTextFieldPreviewTrailingIcon(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            WorthItTextField(
                value = "Trailing Icon",
                modifier = Modifier.padding(8.dp),
                trailingIcon = {
                    WorthItIcon(drawableRes = R.drawable.close_24dp)
                },
                onValueChange = { },
            )
        }
    }
}

@Preview(apiLevel = 36)
@Composable
fun WorthItTextFieldPreviewLabel(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            WorthItTextField(
                value = "Label",
                modifier = Modifier.padding(8.dp),
                label = {
                    WorthItText(text = "Label")
                },
                onValueChange = { },
            )
        }
    }
}

@Preview(apiLevel = 36)
@Composable
fun WorthItTextFieldPreviewError(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            WorthItTextField(
                value = "",
                modifier = Modifier.padding(8.dp),
                errorMessage = "Enter Name",
                label = {
                    WorthItText(text = "Name")
                },
                placeholder = {
                    WorthItText(text = "Name")
                },
                onValueChange = { },
            )
        }
    }
}

@Preview(apiLevel = 36)
@Composable
fun WorthItTextFieldPreviewMultipleLines(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            WorthItTextField(
                value = """
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tincidunt rutrum tempus. 
                    Aliquam ut tempor ex, ac ultrices eros. Vivamus mollis facilisis vehicula. 
                    Fusce at consequat risus. Ut maximus est non sapien egestas, id iaculis magna bibendum. Vivamus lectus urna, feugiat eget turpis sit amet, volutpat pulvinar dui. 
                    Vivamus placerat ex aliquet, blandit metus non, faucibus tortor. 
                    Maecenas convallis odio vel ipsum commodo commodo. In eu urna iaculis, gravida lacus sit amet, blandit mauris. Donec eu diam massa. Donec rutrum erat viverra justo scelerisque consectetur. 
                    Aenean euismod ipsum sit amet elit elementum scelerisque. Ut ac mattis leo. Nullam est turpis, ultrices ac sodales at, pretium a eros.
                """.trimIndent(),
                modifier = Modifier.padding(8.dp),
                maxLines = 16,
                singleLine = false,
                onValueChange = { },
            )
        }
    }
}
