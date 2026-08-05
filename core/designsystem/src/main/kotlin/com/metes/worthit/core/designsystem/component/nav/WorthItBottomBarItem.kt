package com.metes.worthit.core.designsystem.component.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.designsystem.component.other.WorthItText

@Composable
fun RowScope.WorthItBottomBarItem(
    selected: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    @StringRes titleRes: Int,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = contentDescription
            )
        },
        label = { WorthItText(stringResource(titleRes)) }
    )
}
