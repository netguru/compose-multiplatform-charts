package com.netguru.multiplatform.charts.application.navigation

import com.netguru.multiplatform.charts.application.AppAction

sealed class NavigationAction : AppAction {

    object ToggleDrawer : NavigationAction()

    data class OpenTab(val tab: NavigationState.Tab) : NavigationAction()
}
