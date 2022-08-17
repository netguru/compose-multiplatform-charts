package com.netguru.multiplatform.charts.common

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSize {
    COMPACT,
    MEDIUM,
    EXPANDED;

    companion object {
        fun basedOnWidth(windowWidth: Dp): WindowSize {
            return when {
                windowWidth < 600.dp -> COMPACT
                windowWidth < 1000.dp -> MEDIUM
                else -> EXPANDED
            }
        }
    }
}
