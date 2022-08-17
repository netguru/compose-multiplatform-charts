package com.netguru.multiplatform.charts.application

import com.netguru.multiplatform.charts.application.navigation.NavigationAction
import com.netguru.multiplatform.charts.application.navigation.NavigationStore

class ActionDispatcher {

    private val navigationStore = NavigationStore()

    val navigationState by lazy { navigationStore.state }

    fun dispatch(action: AppAction) {
        when (action) {
            is NavigationAction -> navigationStore.dispatch(action)
        }
    }
}
