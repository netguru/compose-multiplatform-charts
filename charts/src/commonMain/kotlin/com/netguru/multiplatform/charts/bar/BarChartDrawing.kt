package com.netguru.multiplatform.charts.bar

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.abs

internal fun DrawScope.drawBarChart(
    data: BarChartData,
    config: BarChartConfig,
    yAxisUpperValue: Float,
    yAxisLowerValue: Float,
    verticalPadding: Float,
    valueScale: List<Float>,
) {
    val canvasHeight = size.height

    val xAxisTickWidth = size.width / (data.categories.size * 2)
    val maxBarsCountInACluster = data.categories.maxOfOrNull { it.entries.size } ?: return
    val maximumThickness = config.thickness.toPx().coerceAtLeast(1f)
    val barWidth = (xAxisTickWidth / maxBarsCountInACluster).coerceIn(1f, maximumThickness)
    val gapsCount = maxBarsCountInACluster - 1
    val freeSpaceInACluster = (xAxisTickWidth - (barWidth * maxBarsCountInACluster))
    val maxHorizontalSpacing = if (gapsCount > 0) freeSpaceInACluster / gapsCount else 0f
    val barsHorizontalSpacing = config.barsSpacing.toPx().coerceIn(0f, maxHorizontalSpacing)

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
            drawRoundRect(
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
                cornerRadius = CornerRadius(config.cornerRadius.toPx(), config.cornerRadius.toPx())
            )
        }
    }
}
