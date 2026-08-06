package com.metes.worthit.core.navigation

import com.metes.worthit.core.common.StandardDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor(
    private val dispatchers: StandardDispatchers
) {
    private val navigationScope = CoroutineScope(SupervisorJob() + dispatchers.main)

    private val _navEvents = Channel<NavigationEvent>(Channel.BUFFERED)
    val navEvents = _navEvents.receiveAsFlow()

    fun navigateTo(screen: Screen) {
        navigationScope.launch {
            _navEvents.send(NavigationEvent.NavigateTo(screen))
        }
    }

    fun navigateBack() {
        navigationScope.launch {
            _navEvents.send(NavigationEvent.NavigateBack)
        }
    }
}

sealed interface NavigationEvent {
    data class NavigateTo(val screen: Screen) : NavigationEvent
    data object NavigateBack : NavigationEvent
}