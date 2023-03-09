package com.netguru.multiplatform.charts.grid

import com.netguru.multiplatform.charts.line.LegendItemData

interface GridChartData {
    val minX: Long
    val maxX: Long
    val minY: Float
    val maxY: Float
    val legendData: List<LegendItemData>
}
