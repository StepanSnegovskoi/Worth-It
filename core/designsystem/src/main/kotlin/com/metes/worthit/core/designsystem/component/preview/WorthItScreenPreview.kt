package com.metes.worthit.core.designsystem.component.preview

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.metes.worthit.core.designsystem.R
import com.metes.worthit.core.designsystem.component.nav.WorthItBottomBarItem
import com.metes.worthit.core.designsystem.theme.AppTheme

enum class PreviewBottomTab {
    Items,
    SaveItem,
    Settings,
}

val PreviewBottomTab.iconRes: Int
   @DrawableRes get() = when(this) {
       PreviewBottomTab.Items -> R.drawable.items_24dp
       PreviewBottomTab.SaveItem -> R.drawable.edit_24dp
       PreviewBottomTab.Settings -> R.drawable.settings_24dp
   }

@Composable
fun WorthItScreenPreview(
    theme: ThemePreviewConfig,
    selectedTab: PreviewBottomTab,
    content: @Composable (scaffoldPadding: PaddingValues, modifier: Modifier) -> Unit
) {
    AppTheme(
        isDarkTheme = theme.isDark,
        primaryThemeColor = theme.color,
    ) {
        Scaffold(
            containerColor = AppTheme.colorScheme.background,
            bottomBar = {
                NavigationBar(containerColor = AppTheme.colorScheme.background) {
                    PreviewBottomTab.entries.forEach { tab ->
                        WorthItBottomBarItem(
                            selected = selectedTab == tab,
                            title = tab.name,
                            iconRes = tab.iconRes,
                            onClick = {}
                        )
                    }
                }
            }
        ) { paddingValues ->
            val screenModifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())

            content(paddingValues, screenModifier)
        }
    }
}
