package com.metes.worthit.app.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.metes.worthit.core.navigation.Screen
import com.metes.worthit.core.navigation.safeNavigateBack
import com.metes.worthit.feature.items.ItemsRoute
import com.metes.worthit.feature.settings.SaveItemRoute
import com.metes.worthit.feature.settings.SaveItemViewModel
import com.metes.worthit.feature.settings.SettingsRoute
import kotlin.uuid.Uuid

@Composable
internal fun AppNavDisplay(
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
                    modifier = Modifier
                        .padding(bottom = scaffoldPadding.calculateBottomPadding())
                        .consumeWindowInsets(
                            PaddingValues(bottom = scaffoldPadding.calculateBottomPadding())
                        ),
                    onNavigateToEditingItem = { itemId ->
                        backStack.add(Screen.SaveItem(itemId = itemId, sessionId = Uuid.random().toString()))
                    },
                    onNavigateToAddingItem = {
                        backStack.add(Screen.SaveItem(sessionId = Uuid.random().toString()))
                    }
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
                    onNavigateToItems = { backStack.safeNavigateBack() },
                    onBackClick = { backStack.safeNavigateBack() },
                )
            }
            entry<Screen.Settings> {
                SettingsRoute(
                    scaffoldPadding = scaffoldPadding,
                )
            }
        }
    )
}
