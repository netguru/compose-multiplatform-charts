package com.netguru.multiplatform.charts.line

import androidx.compose.ui.graphics.Color

data class LineChartColors(
    val grid: Color,
    val surface: Color,
    val overlayLine: Color,
    val overlaySurface: Color = surface,
)
