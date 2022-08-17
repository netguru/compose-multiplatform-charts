package com.netguru.multiplatform.charts.application.navigation

data class NavigationState(
    val isDrawerOpened: Boolean = false,
    val currentTab: Tab = Tab.BAR
) {
    enum class Tab {
        BAR,
        BUBBLE,
        DIAL,
        GAS_BOTTLE,
        LINE,
        PIE,
    }
}
