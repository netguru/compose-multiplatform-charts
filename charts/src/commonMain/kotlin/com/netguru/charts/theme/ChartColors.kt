package com.netguru.charts.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ChartColors internal constructor(
    val primary: Color,
    val primaryText: Color,
    val surface: Color,
    val borders: Color,
    val grid: Color,
    val labels: Color,
    val bubbleText: Color,
    val emptyGasBottle: Color,
    val fullGasBottle: Color,
    val overlayLine: Color,
)
