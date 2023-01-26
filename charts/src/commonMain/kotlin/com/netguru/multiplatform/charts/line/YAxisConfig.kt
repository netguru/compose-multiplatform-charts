package com.netguru.multiplatform.charts.line

import androidx.compose.runtime.Composable
import com.netguru.multiplatform.charts.grid.ChartGridDefaults
import com.netguru.multiplatform.charts.grid.YAxisTitleData
import com.netguru.multiplatform.charts.grid.axisscale.y.YAxisScale

data class YAxisConfig(
    val markerLayout: (@Composable (value: Any) -> Unit)? = ChartGridDefaults.YAxisMarkerLayout,
    val yAxisTitleData: YAxisTitleData? = ChartGridDefaults.YAxisDataTitle,
    val scale: YAxisScale,
)
