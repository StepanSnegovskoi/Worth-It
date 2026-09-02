package com.metes.worthit.core.designsystem.component.image

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.preview.BackgroundForPreview

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

@Preview
@Composable
fun WorthItIconPreview() {
    BackgroundForPreview {
        WorthItIcon(
            drawableRes = R.drawable.edit_24dp
        )
    }
}
