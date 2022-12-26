package com.netguru.multiplatform.charts.line

import androidx.compose.runtime.Composable
import com.netguru.multiplatform.charts.grid.GridDefaults
import com.netguru.multiplatform.charts.grid.YAxisTitleData

data class YAxisData(
    val lineChartData: LineChartData,
    val markerLayout: (@Composable (value: Any) -> Unit)? = GridDefaults.YAxisMarkerLayout,
    val yAxisTitleData: YAxisTitleData? = GridDefaults.YAxisDataTitleYAxisData,
    val roundMinMaxClosestTo: Float = GridDefaults.ROUND_Y_AXIS_MIN_MAX_CLOSEST_TO,
)
