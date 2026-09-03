package com.metes.worthit.core.designsystem.component.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import coil3.compose.AsyncImage
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItImage(
    @DrawableRes defaultImageDrawableRes: Int,
    contentDescription: String?,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter = ColorFilter.tint(color = AppTheme.colorScheme.primary),
    model: Any? = null,
) {
    if (model != null) {
        AsyncImage(
            modifier = modifier,
            model = model,
            contentDescription = contentDescription,
            contentScale = contentScale,
        )
    } else {
        Image(
            modifier = modifier,
            painter = painterResource(defaultImageDrawableRes),
            contentDescription = null,
            colorFilter = colorFilter,
        )
    }
}

@Preview(name = "Default image", widthDp = 48, heightDp = 48)
@Composable
fun WorthItImagePreviewDefault(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        WorthItImage(
            defaultImageDrawableRes = R.drawable.error_24dp,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(name = "Model image", widthDp = 48, heightDp = 48)
@Composable
fun WorthItImagePreviewModel(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        WorthItImage(
            model = R.drawable.edit_24dp,
            defaultImageDrawableRes = R.drawable.error_24dp,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(color = AppTheme.colorScheme.primary),
        )
    }
}
