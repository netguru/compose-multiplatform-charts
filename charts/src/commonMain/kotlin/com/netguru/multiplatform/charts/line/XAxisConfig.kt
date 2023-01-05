package com.netguru.multiplatform.charts.line

import androidx.compose.runtime.Composable
import com.netguru.multiplatform.charts.grid.ChartGridDefaults

data class XAxisConfig(
    val markerLayout: @Composable (value: Any) -> Unit = ChartGridDefaults.XAxisMarkerLayout,
    val hideMarkersWhenOverlapping: Boolean = false,
    val alignFirstAndLastToChartEdges: Boolean = false,
    val roundMinMaxClosestTo: Long = ChartGridDefaults.ROUND_X_AXIS_MIN_MAX_CLOSEST_TO,
    val maxVerticalLines: Int = ChartGridDefaults.NUMBER_OF_GRID_LINES,
)
