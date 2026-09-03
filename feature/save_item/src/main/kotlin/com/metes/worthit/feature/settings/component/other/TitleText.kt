package com.metes.worthit.feature.settings.component.other

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.component.text.WorthItText
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.save_item.R

@Composable
internal fun TitleText(
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

@Preview
@Composable
private fun TitleTextPreviewEditingMode(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        TitleText(isEditingMode = true)
    }
}

@Preview
@Composable
private fun TitleTextPreviewAddingMode(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        TitleText(isEditingMode = false)
    }
}
