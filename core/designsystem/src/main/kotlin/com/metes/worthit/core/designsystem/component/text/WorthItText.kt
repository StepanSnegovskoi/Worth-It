package com.metes.worthit.core.designsystem.component.text

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.metes.worthit.core.designsystem.component.preview.BackgroundForPreview

@Composable
fun WorthItText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    style: TextStyle = LocalTextStyle.current,
    color: Color = LocalContentColor.current,
) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow,
        style = style,
        color = color,
    )
}

@Preview
@Composable
fun WorthItTextPreview() {
    BackgroundForPreview {
        WorthItText(
            text = "Hello World",
        )
    }
}
