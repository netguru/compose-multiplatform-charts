package com.netguru.multiplatform.charts.application.home

import androidx.compose.runtime.Composable
import com.netguru.multiplatform.charts.application.navigation.NavigationState

@Composable
fun NavigationState.Tab.toLabel(): String = when (this) {
    NavigationState.Tab.BAR -> "Bar"
    NavigationState.Tab.BUBBLE -> "Bubble"
    NavigationState.Tab.DIAL -> "Dial"
    NavigationState.Tab.GAS_BOTTLE -> "Gas bottle"
    NavigationState.Tab.LINE -> "Line"
    NavigationState.Tab.LINE_WITH_TWO_Y_AXIS -> "Line with two Y axis"
    NavigationState.Tab.PIE -> "Pie"
}
