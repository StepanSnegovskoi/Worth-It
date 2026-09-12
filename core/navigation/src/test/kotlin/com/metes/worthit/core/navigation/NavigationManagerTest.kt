package com.metes.worthit.core.navigation

import app.cash.turbine.test
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NavigationManagerTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var navigationManager: NavigationManager

    @Before
    fun setup() {
        navigationManager = NavigationManager(mainDispatcher = dispatcher)
    }

    @Test
    fun `navEvents provides events correctly`() = runTest(dispatcher) {
        navigationManager.navEvents.test {
            navigationManager.navigateTo(Screen.Items)
            assertEquals(NavigationEvent.NavigateTo(Screen.Items), awaitItem())

            navigationManager.navigateTo(Screen.SaveItem(sessionId = "sessionIdTest"))
            assertEquals(
                NavigationEvent.NavigateTo(Screen.SaveItem(sessionId = "sessionIdTest")),
                awaitItem()
            )

            navigationManager.navigateTo(Screen.Settings)
            assertEquals(NavigationEvent.NavigateTo(Screen.Settings), awaitItem())

            navigationManager.navigateTo(Screen.Items)
            assertEquals(NavigationEvent.NavigateTo(Screen.Items), awaitItem())
        }
    }
}
