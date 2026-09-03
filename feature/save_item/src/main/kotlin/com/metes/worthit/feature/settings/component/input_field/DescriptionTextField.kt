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
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.input.WorthItTextField
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.save_item.R
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
internal fun DescriptionTextField(
    description: String,
    modifier: Modifier = Modifier,
    onRemoveDescriptionClick: () -> Unit,
    onDescriptionChange: (String) -> Unit,
) {
    WorthItTextField(
        modifier = modifier.fillMaxWidth(),
        value = description,
        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
        onValueChange = onDescriptionChange,
        maxLines = Int.MAX_VALUE,
        singleLine = false,
        label = { WorthItText(text = stringResource(R.string.description_hint)) },
        trailingIcon = {
            if (description.isNotEmpty()) {
                WorthItIconButton(
                    onClick = onRemoveDescriptionClick
                ) {
                    WorthItIcon(
                        drawableRes = DesignR.drawable.close_24dp,
                        contentDescriptionRes = R.string.cd_clear_description,
                    )
                }
            }
        },
        placeholder = {
            WorthItText(text = stringResource(R.string.placeholder_bike_description))
        },
    )
}

@Preview
@Composable
private fun DescriptionTextFieldPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(color = AppTheme.colorScheme.background) {
            DescriptionTextField(
                modifier = Modifier.padding(8.dp),
                description = """
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
                    Donec ornare est non est sagittis, a ultricies quam dictum.
                """.trimIndent(),
                onRemoveDescriptionClick = { },
                onDescriptionChange = { },
            )
        }
    }
}
