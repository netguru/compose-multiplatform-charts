package com.netguru.charts.gridchart

import com.netguru.charts.line.LegendItemData

interface GridChartData {
    val minX: Long
    val maxX: Long
    val minY: Float
    val maxY: Float
    val legendData: List<LegendItemData>
}
