package com.metes.worthit.core.designsystem.component.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.metes.worthit.core.designsystem.theme.PrimaryThemeColor

class ThemePreviewParameter : PreviewParameterProvider<ThemePreviewConfig> {

    private val themes = buildList {
        PrimaryThemeColor.entries.forEach { themeColor ->
            add(ThemePreviewConfig(themeColor, true))
            add(ThemePreviewConfig(themeColor, false))
        }
    }

    override val values: Sequence<ThemePreviewConfig>
        get() = themes.asSequence()

    override fun getDisplayName(index: Int): String {
        val color = themes[index].color
        val isDarkString = if (themes[index].isDark) "Dark" else "Light"

        return "$color - $isDarkString"
    }
}

data class ThemePreviewConfig(
    val color: PrimaryThemeColor,
    val isDark: Boolean,
)
