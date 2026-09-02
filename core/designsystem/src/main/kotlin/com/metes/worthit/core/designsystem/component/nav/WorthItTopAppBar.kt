package com.metes.worthit.core.designsystem.component.nav

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.button.WorthItIconButton
import com.metes.worthit.core.designsystem.component.defaults.WorthItTopAppBarDefaults
import com.metes.worthit.core.designsystem.component.image.WorthItIcon
import com.metes.worthit.core.designsystem.component.preview.BackgroundForPreview
import com.metes.worthit.core.designsystem.component.text.WorthItText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorthItTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        colors = WorthItTopAppBarDefaults.colors(),
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun WorthItTopAppBarPreview() {
    BackgroundForPreview(transparent = true) {
        WorthItTopAppBar(
            title = {
                WorthItText(text = stringResource(R.string.preview_items))
            },
            navigationIcon = {
                WorthItIconButton(
                    onClick = {}) {
                    WorthItIcon(drawableRes = R.drawable.back_24dp)
                }
            },
            actions = {
                Row {
                    WorthItIconButton(
                        onClick = {}) {
                        WorthItIcon(drawableRes = R.drawable.close_24dp)
                    }
                    WorthItIconButton(
                        onClick = {}) {
                        WorthItIcon(drawableRes = R.drawable.add_24dp)
                    }
                }
            },
        )
    }
}
