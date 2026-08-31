package com.metes.worthit.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.designsystem.component.defaults.WorthItTopAppBarDefaults
import com.metes.worthit.core.designsystem.component.other.WorthItIcon
import com.metes.worthit.core.designsystem.component.other.WorthItIconButton
import com.metes.worthit.core.designsystem.component.other.WorthItText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorthItTopAppBar(
    @StringRes titleStringRes: Int,
    @DrawableRes navigationIconRes: Int,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
    @StringRes navigationIconContentDescriptionStringRes: Int? = null,
    onNavigationIconClick: () -> Unit,
) {
    TopAppBar(
        title = {
            WorthItText(text = stringResource(titleStringRes))
        },
        modifier = modifier,
        colors = WorthItTopAppBarDefaults.colors(),
        navigationIcon = {
            WorthItIconButton(onClick = onNavigationIconClick) {
                WorthItIcon(
                    drawableRes = navigationIconRes,
                    contentDescriptionRes = navigationIconContentDescriptionStringRes
                )
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior,
    )
}
