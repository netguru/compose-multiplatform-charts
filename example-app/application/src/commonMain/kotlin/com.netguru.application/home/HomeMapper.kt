package com.netguru.application.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import com.netguru.application.navigation.NavigationState

@Composable
fun NavigationState.Tab.toLabel(): String = when (this) {
    NavigationState.Tab.BAR -> "Bar"
    NavigationState.Tab.BUBBLE -> "Bubble"
    NavigationState.Tab.DIAL -> "Dial"
    NavigationState.Tab.GAS_BOTTLE -> "Gas bottle"
    NavigationState.Tab.LINE -> "Line"
    NavigationState.Tab.PIE -> "Pie"
}
