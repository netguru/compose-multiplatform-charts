package com.netguru.multiplatform.charts.line

import androidx.compose.runtime.Composable
import com.netguru.multiplatform.charts.grid.GridDefaults

data class XAxisData(
    val markerLayout: @Composable (value: Any) -> Unit = GridDefaults.XAxisMarkerLayout,
    val hideMarkersWhenOverlapping: Boolean = false,
    val alignFirstAndLastToChartEdges: Boolean = false,
    val roundMinMaxClosestTo: Long = GridDefaults.ROUND_X_AXIS_MIN_MAX_CLOSEST_TO,
)
