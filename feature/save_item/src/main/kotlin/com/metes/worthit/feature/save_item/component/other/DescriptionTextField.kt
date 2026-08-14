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
import com.metes.worthit.core.designsystem.component.other.WorthItOutlinedTextField
import com.metes.worthit.feature.save_item.R
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
fun DescriptionTextField(
    description: String,
    modifier: Modifier = Modifier,
    onRemoveDescriptionClick: () -> Unit,
    onDescriptionChange: (String) -> Unit,
) {
    WorthItOutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = description,
        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
        onValueChange = onDescriptionChange,
        maxLines = Int.MAX_VALUE,
        singleLine = false,
        label = { Text(text = stringResource(R.string.description_hint)) },
        trailingIcon = {
            if (description.isNotEmpty()) {
                IconButton(
                    onClick = onRemoveDescriptionClick
                ) {
                    Icon(
                        painter = painterResource(DesignR.drawable.close_24dp),
                        contentDescription = null
                    )
                }
            }
        }
    )
}
