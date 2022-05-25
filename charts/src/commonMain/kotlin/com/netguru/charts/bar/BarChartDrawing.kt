package com.netguru.charts.bar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.abs

const val BAR_MAX_WIDTH = 20f
const val BAR_MAX_HORIZONTAL_SPACING = 10f

internal fun DrawScope.drawBarChart(
    data: BarChartData,
    yAxisUpperValue: Float,
    yAxisLowerValue: Float,
    verticalPadding: Float,
    valueScale: List<Float>,
) {
    val canvasHeight = size.height

    val xAxisTickWidth = size.width / (data.categories.size * 2)
    val maxBarsCountInACluster = data.categories.maxOfOrNull { it.entries.size } ?: return
    val barWidth = (xAxisTickWidth / maxBarsCountInACluster).coerceAtMost(BAR_MAX_WIDTH)
    val barsHorizontalSpacing = BAR_MAX_HORIZONTAL_SPACING / maxBarsCountInACluster

    val gridHeight = canvasHeight - 2 * verticalPadding
    val yAxisZeroPosition = verticalPadding +
        yAxisUpperValue / (yAxisUpperValue - yAxisLowerValue) * gridHeight

    data.categories.forEachIndexed { categoryIndex, category ->
        val clusterWidth = category.entries.size * barWidth +
            (category.entries.size - 1) * barsHorizontalSpacing
        val clusterXOffset = (categoryIndex * 2 + 1) * xAxisTickWidth - clusterWidth / 2

        category.entries.forEachIndexed { entryIndex, entry ->
            val clusterHeight = gridHeight * valueScale[entryIndex]
            val barHeight = abs(entry.y) / (yAxisUpperValue - yAxisLowerValue) * clusterHeight
            drawRect(
                color = entry.color,
                topLeft = Offset(
                    x = clusterXOffset + entryIndex * (barWidth + barsHorizontalSpacing),
                    y = if (entry.y >= 0) {
                        yAxisZeroPosition - barHeight
                    } else {
                        yAxisZeroPosition
                    }
                ),
                size = Size(
                    width = barWidth,
                    height = barHeight
                ),
            )
        }
    }
}
