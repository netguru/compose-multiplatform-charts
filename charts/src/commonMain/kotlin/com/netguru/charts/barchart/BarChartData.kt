package com.netguru.charts.barchart

import androidx.compose.runtime.Immutable
import com.netguru.charts.gridchart.GridChartData
import com.netguru.charts.line.LegendItemData
import com.netguru.charts.line.SymbolShape

@Immutable
data class BarChartData(
    val categories: List<BarChartCategory>
) : GridChartData {
    override val minX: Long = 0
    override val maxX: Long
        get() = if (categories.isEmpty()) {
            1
        } else {
            categories.size * 2.toLong()
        }

    override val minY: Float
        get() = 0f

    override val maxY: Float
        get() = if (categories.isEmpty()) {
            1f
        } else {
            categories.maxOf { it.maxY }
        }

    override val legendData: List<LegendItemData>
        get() {
            return categories
                .flatMap { it.entries }
                .distinctBy { it.x }
                .map {
                    LegendItemData(
                        name = it.x,
                        symbolShape = SymbolShape.RECTANGLE,
                        color = it.color,
                        dashed = false,
                    )
                }
        }
}
