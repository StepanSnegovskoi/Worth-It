package com.metes.worthit.feature.items.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.surface.ContentWrapper
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.items.R

@Composable
internal fun ItemsListIsEmpty(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ContentWrapper(
        modifier = modifier,
        onClick = onClick,
        alpha = AppTheme.alpha.low,
        color = AppTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            WorthItText(
                text = stringResource(R.string.the_list_of_items_is_empty),
                maxLines = Int.MAX_VALUE,
                color = AppTheme.colorScheme.onSurface,
            )
            WorthItIcon(drawableRes = R.drawable.wind_40dp, tint = AppTheme.colorScheme.primary)
            WorthItText(
                text = stringResource(R.string.let_s_add_something),
                maxLines = Int.MAX_VALUE,
                color = AppTheme.colorScheme.onSurface,
            )
        }
    }
}
