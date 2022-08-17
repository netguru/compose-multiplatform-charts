package com.netguru.multiplatform.charts.bar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.netguru.multiplatform.charts.theme.ChartColors

@Immutable
data class BarChartColors(
    val grid: Color,
)

val ChartColors.barChartColors
    get() = BarChartColors(
        grid = grid
    )
