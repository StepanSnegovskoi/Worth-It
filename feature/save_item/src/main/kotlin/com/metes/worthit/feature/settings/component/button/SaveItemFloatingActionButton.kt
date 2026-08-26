package com.metes.worthit.feature.settings.component.button

import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.theme.AppTheme
import com.metes.worthit.feature.save_item.R
import com.metes.worthit.core.designsystem.R as DesignR

@Composable
internal fun SaveItemFloatingActionButton(
    isEditingMode: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        containerColor = AppTheme.colorScheme.primary,
        onClick = onClick,
    ) {
        val iconRes =
            if (isEditingMode) DesignR.drawable.done_24dp else DesignR.drawable.add_24dp
        val contentDescriptionRes =
            if (isEditingMode) R.string.cd_save_changes else R.string.cd_add_item

        WorthItIcon(
            drawableRes = iconRes,
            contentDescriptionRes = contentDescriptionRes,
            tint = AppTheme.colorScheme.onPrimary,
        )
    }
}
