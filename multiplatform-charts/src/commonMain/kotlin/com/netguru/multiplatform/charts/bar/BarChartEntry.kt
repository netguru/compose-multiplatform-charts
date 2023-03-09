package com.netguru.multiplatform.charts.bar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class BarChartEntry(
    val x: String,
    val y: Float,
    val color: Color
)
