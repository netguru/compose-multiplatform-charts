package com.netguru.multiplatform.charts.bar

import androidx.compose.runtime.Immutable
import com.netguru.multiplatform.charts.grid.GridChartData
import com.netguru.multiplatform.charts.line.LegendItemData
import com.netguru.multiplatform.charts.line.SymbolShape

@Immutable
data class BarChartData(
    val categories: List<BarChartCategory>,
    val unit: String?,
) : GridChartData {
    // TODO hide those values from the user
    override val minX: Long = 0
    override val maxX: Long
        get() = if (categories.isEmpty()) {
            1
        } else {
            categories.size * 2.toLong()
        }

    override val minY: Float
        get() = if (categories.isEmpty()) {
            0f
        } else {
            categories.minOf { it.minY }.coerceAtMost(0f)
        }

    override val maxY: Float
        get() = if (categories.isEmpty()) {
            1f
        } else {
            categories.maxOf { it.maxY }.coerceAtLeast(0f)
        }

    override val legendData: List<LegendItemData>
        get() {
            return categories
                .flatMap { it.entries }
                .distinctBy { it.x }
                .map {
                    LegendItemData(
                        name = it.x,
                        unit = unit,
                        symbolShape = SymbolShape.RECTANGLE,
                        color = it.color,
                        pathEffect = null,
                    )
                }
        }
}
