package com.metes.worthit.feature.items.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.defaults.WorthItOutlinedTextFieldDefaults
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.items.R
import com.metes.worthit.core.designsystem.R as DesignR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    colors: TextFieldColors = WorthItOutlinedTextFieldDefaults.colors(),
    onCleanClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SearchField(
            value = value,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            enabled = enabled,
            colors = colors,
            isError = isError,
            modifier = Modifier.weight(1f),
        )
        AnimatedVisibility(visible = value.isNotEmpty()) {
            WorthItIconButton(onClick = onCleanClick) {
                WorthItIcon(
                    drawableRes = DesignR.drawable.close_24dp,
                    contentDescriptionRes = R.string.cd_search,
                    modifier = Modifier.size(32.dp),
                    tint = AppTheme.colorScheme.primary,
                )
            }
        }
        WorthItIcon(
            drawableRes = R.drawable.search_24dp,
            contentDescriptionRes = R.string.cd_search,
            modifier = Modifier.size(32.dp),
            tint = AppTheme.colorScheme.primary,
        )
    }
}
