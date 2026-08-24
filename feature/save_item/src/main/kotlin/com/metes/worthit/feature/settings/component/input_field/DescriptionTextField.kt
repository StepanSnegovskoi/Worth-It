package com.metes.worthit.feature.settings.component.input_field

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItIconButton
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.component.other.WorthItTextField
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
