package com.netguru.application.navigation

import com.netguru.application.AppAction

sealed class NavigationAction : AppAction {

    object ToggleDrawer : NavigationAction()

    data class OpenTab(val tab: NavigationState.Tab) : NavigationAction()
}
