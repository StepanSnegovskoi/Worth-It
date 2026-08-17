package com.metes.worthit.core.designsystem.component.other

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItIcon(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier,
    @StringRes contentDescriptionRes: Int? = null,
    tint: Color = AppTheme.colorScheme.primary,
) {
    Icon(
        modifier = modifier,
        tint = tint,
        painter = painterResource(drawableRes),
        contentDescription = contentDescriptionRes?.let {
            stringResource(contentDescriptionRes)
        }
    )
}