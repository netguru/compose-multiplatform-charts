package com.netguru.multiplatform.charts.line

import androidx.compose.runtime.Composable
import com.netguru.multiplatform.charts.grid.ChartGridDefaults
import com.netguru.multiplatform.charts.grid.YAxisTitleData

data class YAxisConfig(
    val markerLayout: (@Composable (value: Any) -> Unit)? = ChartGridDefaults.YAxisMarkerLayout,
    val yAxisTitleData: YAxisTitleData? = ChartGridDefaults.YAxisDataTitleYAxisData,
    val roundMinMaxClosestTo: Float = ChartGridDefaults.ROUND_Y_AXIS_MIN_MAX_CLOSEST_TO,
    val maxHorizontalLines: Int = ChartGridDefaults.NUMBER_OF_GRID_LINES,
)
