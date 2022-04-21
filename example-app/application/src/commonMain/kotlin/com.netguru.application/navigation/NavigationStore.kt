package com.netguru.application.navigation

import com.netguru.application.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class NavigationStore(
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : Store<NavigationAction, NavigationState>(NavigationState(), dispatcher) {

    init {
        onAction<NavigationAction.ToggleDrawer> { toggleDrawer() }
        onAction<NavigationAction.OpenTab> { openTab(it.tab) }
    }

    private fun toggleDrawer() {
        updateState { copy(isDrawerOpened = isDrawerOpened.not()) }
    }

    private fun openTab(tab: NavigationState.Tab) {
        updateState { copy(currentTab = tab, isDrawerOpened = false) }
    }
}
