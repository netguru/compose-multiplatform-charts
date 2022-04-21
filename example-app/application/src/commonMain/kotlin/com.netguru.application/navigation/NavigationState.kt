package com.netguru.application.navigation

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
