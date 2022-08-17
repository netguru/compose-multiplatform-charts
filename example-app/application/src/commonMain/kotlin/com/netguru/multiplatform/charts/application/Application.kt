package com.netguru.multiplatform.charts.application

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.netguru.multiplatform.charts.application.home.HomeScreen
import com.netguru.multiplatform.charts.common.AppTheme
import com.netguru.multiplatform.charts.common.WindowSize

val LocalActionDispatcher = staticCompositionLocalOf { ActionDispatcher() }

@Composable
fun Application(windowSize: WindowSize) {
    AppTheme(windowSize) {
        HomeScreen()
    }
}
