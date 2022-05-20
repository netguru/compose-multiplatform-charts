package com.netguru.charts.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ChartColors internal constructor(
    val primary: Color,
    val surface: Color,
    val grid: Color,
    val emptyGasBottle: Color,
    val fullGasBottle: Color,
    val overlayLine: Color,
)
