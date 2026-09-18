package com.metes.worthit.feature.items.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.feature.items.R

@Composable
internal fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    interactionSource: MutableInteractionSource,
    enabled: Boolean,
    colors: TextFieldColors,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = true,
        textStyle = TextStyle(color = colors.focusedTextColor),
        cursorBrush = SolidColor(colors.cursorColor),
        decorationBox = @Composable { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                placeholder = {
                    WorthItText(text = stringResource(R.string.search))
                }
            )
        }
    )
}
