package com.metes.worthit.feature.settings.component.other

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.designsystem.component.other.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.save_item.R

@Composable
fun TitleText(
    isEditingMode: Boolean,
    modifier: Modifier = Modifier,
) {
    val titleRes =
        if (isEditingMode) R.string.editing_item else R.string.adding_item

    WorthItText(
        text = stringResource(titleRes),
        modifier = modifier,
        color = AppTheme.colorScheme.onBackground,
    )
}
