package com.metes.worthit.core.designsystem.component.other

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun WorthItIcon(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier,
    @StringRes contentDescriptionRes: Int? = null,
    tint: Color = LocalContentColor.current,
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