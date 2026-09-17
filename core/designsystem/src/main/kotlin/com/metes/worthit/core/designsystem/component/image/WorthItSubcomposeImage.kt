package com.metes.worthit.core.designsystem.component.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewConfig
import com.metes.worthit.core.designsystem.component.preview.ThemePreviewParameter
import com.metes.worthit.core.designsystem.theme.AppTheme

@Composable
fun WorthItSubcomposeImage(
    model: Any?,
    @DrawableRes defaultImageDrawableRes: Int,
    modifier: Modifier = Modifier,
    progressBarColor: Color = AppTheme.colorScheme.onBackground,
    contentScale: ContentScale = ContentScale.Crop,
    colorFilter: ColorFilter = ColorFilter.tint(AppTheme.colorScheme.onBackground),
    contentDescription: String? = null,
) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
    ) {
        val state by painter.state.collectAsStateWithLifecycle()

        when (state) {
            AsyncImagePainter.State.Empty -> {
                Image(
                    painter = painterResource(defaultImageDrawableRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    colorFilter = colorFilter,
                )
            }

            is AsyncImagePainter.State.Error -> {
                if (model == null) {
                    Image(
                        painter = painterResource(defaultImageDrawableRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        colorFilter = colorFilter,
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.error_24dp),
                        contentDescription = stringResource(R.string.cd_failed_to_load_image),
                        modifier = Modifier.fillMaxSize(),
                        colorFilter = colorFilter,
                    )
                }
            }

            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = progressBarColor,
                    )
                }
            }

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
}

@Preview(name = "Default image", widthDp = 48, heightDp = 48)
@Composable
fun WorthItSubcomposeImagePreviewDefault(
    @PreviewParameter(ThemePreviewParameter::class) theme: ThemePreviewConfig
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        WorthItSubcomposeImage(
            model = null,
            defaultImageDrawableRes = R.drawable.error_24dp,
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}
