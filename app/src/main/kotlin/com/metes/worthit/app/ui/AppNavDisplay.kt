package com.metes.worthit.app.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.feature.items.ItemsRoute
import com.metes.worthit.feature.items.SettingsScreen
import com.metes.worthit.feature.save_item.SaveItemRoute
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import com.metes.worthit.feature.save_item.SaveItemViewModel

@Composable
fun AppNavDisplay(
    backStack: NavBackStack<Screen>,
    scaffoldPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Screen.Items> {
                ItemsRoute(
                    scaffoldPadding = scaffoldPadding,
                    onNavigateToSaveItem = { itemId ->
                        backStack.add(Screen.SaveItem(itemId = itemId))
                    },
                )
            }
            entry<Screen.SaveItem> { key ->
                val viewModel = hiltViewModel<SaveItemViewModel, SaveItemViewModel.Factory>(
                    creationCallback = { factory ->
                        factory.create(itemId = key.itemId, imagePath = key.imagePath)
                    }
                )

                SaveItemRoute(
                    viewModel = viewModel,
                    modifier = Modifier
                        .padding(scaffoldPadding)
                        .consumeWindowInsets(scaffoldPadding),
                    onNavigateToItems = { backStack.removeLastOrNull() },
                    onBackClick = { backStack.removeLastOrNull() },
                )
            }
            entry<Screen.Settings> {
                SettingsScreen(
                    scaffoldPadding = scaffoldPadding,
                )
            }
        }
    )
}
