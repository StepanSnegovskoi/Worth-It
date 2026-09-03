package com.metes.worthit.feature.settings.component.input_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.component.input.WorthItTextField
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.presentation.UiText
import com.metes.worthit.feature.save_item.R
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
internal fun NameTextField(
    name: String,
    nameError: UiText?,
    modifier: Modifier = Modifier,
    onRemoveNameClick: () -> Unit,
    onNameChange: (String) -> Unit,
) {
    WorthItTextField(
        modifier = modifier.fillMaxWidth(),
        value = name,
        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
        onValueChange = onNameChange,
        errorMessage = nameError?.asString(),
        label = { WorthItText(text = stringResource(R.string.name_hint)) },
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
        placeholder = {
            WorthItText(text = stringResource(R.string.placeholder_bike_name))
        },
    )
}

@Preview
@Composable
private fun NameTextFieldPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            NameTextField(
                modifier = Modifier.padding(8.dp),
                name = "Bike",
                onRemoveNameClick = { },
                nameError = null,
                onNameChange = { },
            )
        }
    }
}
