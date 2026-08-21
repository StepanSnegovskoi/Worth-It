package com.metes.worthit.feature.settings.component.other

import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metes.worthit.core.designsystem.component.defaults.WorthItIconButtonDefaults
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItIconButton
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.save_item.R
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
fun SaveItemTopBarButton(
    isEditingMode: Boolean,
    modifier: Modifier = Modifier,
    colors: IconButtonColors = WorthItIconButtonDefaults.colors(),
    onClick: () -> Unit,
) {
    WorthItIconButton(
        modifier = modifier,
        colors = colors.copy(
            containerColor = AppTheme.colorScheme.primary,
            contentColor = AppTheme.colorScheme.onPrimary
        ),
        onClick = onClick,
    ) {
        val iconRes =
            if (isEditingMode) DesignR.drawable.done_24dp else DesignR.drawable.add_24dp
        val contentDescriptionRes =
            if (isEditingMode) R.string.cd_save_changes else R.string.cd_add_item

        WorthItIcon(
            drawableRes = iconRes,
            contentDescriptionRes = contentDescriptionRes,
        )
    }
}
