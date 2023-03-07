package com.netguru.multiplatform.charts.bar

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.netguru.multiplatform.charts.mapValueToDifferentRange

internal fun DrawScope.drawBarChart(
    data: BarChartData,
    config: BarChartConfig,
    yAxisUpperValue: Float,
    yAxisLowerValue: Float,
    valueScale: List<Float>,
    yAxisZeroPosition: Float
): List<BarChartBar> {
    val xAxisTickWidth = size.width / (data.categories.size * 2)
    val maxBarsCountInACluster =
        data.categories.maxOfOrNull { it.entries.size } ?: return emptyList()
    val maximumThickness = config.thickness.toPx().coerceAtLeast(1f)
    val barWidth = (xAxisTickWidth / maxBarsCountInACluster).coerceIn(1f, maximumThickness)
    val gapsCount = maxBarsCountInACluster - 1
    val freeSpaceInACluster = (xAxisTickWidth - (barWidth * maxBarsCountInACluster))
    val maxHorizontalSpacing = if (gapsCount > 0) freeSpaceInACluster / gapsCount else 0f
    val barsHorizontalSpacing = config.barsSpacing.toPx().coerceIn(0f, maxHorizontalSpacing)
    return data.categories.flatMapIndexed { categoryIndex, category ->
        val clusterWidth = category.entries.size * barWidth +
            (category.entries.size - 1) * barsHorizontalSpacing
        val clusterXOffset = (categoryIndex * 2 + 1) * xAxisTickWidth - clusterWidth / 2

        category.entries.mapIndexed { entryIndex, entry ->
            val x = clusterXOffset + entryIndex * (barWidth + barsHorizontalSpacing)
            val y = entry.y * valueScale[entryIndex]
            val currentPosition = y.mapValueToDifferentRange(
                yAxisLowerValue,
                yAxisUpperValue,
                size.height,
                0f
            )
            val barHeight = if (currentPosition < yAxisZeroPosition) {
                yAxisZeroPosition - currentPosition
            } else {
                currentPosition - yAxisZeroPosition
            }
            val newY = if (entry.y > 0) {
                currentPosition
            } else {
                yAxisZeroPosition
            }
            drawRoundRect(
                color = entry.color,
                topLeft = Offset(
                    x = x,
                    y = newY
                ),
                size = Size(
                    width = barWidth,
                    height = barHeight
                ),
                cornerRadius = CornerRadius(config.cornerRadius.toPx(), config.cornerRadius.toPx())
            )
            BarChartBar(
                width = x..(x + barWidth),
                height = newY..(newY + barHeight),
                data = entry
            )
        }
    }
}
