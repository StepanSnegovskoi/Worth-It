package com.metes.worthit.core.designsystem.component.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.defaults.WorthItNavigationBarDefaults
import com.metes.worthit.core.designsystem.component.preview.BackgroundForPreview
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.text.WorthItText

@Composable
fun RowScope.WorthItBottomBarItem(
    selected: Boolean,
    modifier: Modifier = Modifier,
    @StringRes contentDescriptionRes: Int? = null,
    @StringRes titleRes: Int,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        modifier = modifier,
        onClick = onClick,
        icon = {
            WorthItIcon(
                drawableRes = iconRes,
                contentDescriptionRes = contentDescriptionRes,
            )
        },
        colors = WorthItNavigationBarDefaults.colors(),
        label = { WorthItText(stringResource(titleRes)) },
    )
}

@Preview
@Composable
fun WorthItBottomBarItemPreview() {
    BackgroundForPreview(transparent = false) {
        Row {
            WorthItBottomBarItem(
                selected = true,
                onClick = {},
                titleRes = R.string.preview_items,
                iconRes = R.drawable.items_24dp
            )
        }
    }
}
