package com.netguru.application

import com.netguru.application.navigation.NavigationAction
import com.netguru.application.navigation.NavigationStore

class ActionDispatcher {

    private val navigationStore = NavigationStore()

    val navigationState by lazy { navigationStore.state }

    fun dispatch(action: AppAction) {
        when (action) {
            is NavigationAction -> navigationStore.dispatch(action)
        }
    }
}
