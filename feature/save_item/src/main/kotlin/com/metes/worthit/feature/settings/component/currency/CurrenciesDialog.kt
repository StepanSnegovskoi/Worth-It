package com.metes.worthit.feature.settings.component.currency

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.metes.worthit.core.designsystem.component.dialog.WorthItBasicAlertDialog
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.core.domain.entity.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CurrenciesDialog(
    show: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues.Zero,
    onCurrencyClick: (Currency) -> Unit,
) {
    WorthItBasicAlertDialog(
        visible = show,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Currencies(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .heightIn(max = 480.dp)
                .wrapContentHeight(),
            contentPadding = contentPadding,
            onClick = onCurrencyClick,
        )
    }
}

@Preview
@Composable
private fun CurrenciesDialogPreview(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppTheme.colorScheme.background,
        ) {
            CurrenciesDialog(
                show = true,
                contentPadding = PaddingValues(vertical = 16.dp),
                onDismissRequest = { },
                onCurrencyClick = { },
            )
        }
    }
}
