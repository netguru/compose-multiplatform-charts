package com.netguru.application

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.netguru.application.home.HomeScreen
import com.netguru.common.AppTheme
import com.netguru.common.WindowSize

val LocalActionDispatcher = staticCompositionLocalOf { ActionDispatcher() }

@Composable
fun Application(windowSize: WindowSize) {
    AppTheme(windowSize) {
        HomeScreen()
    }
}
