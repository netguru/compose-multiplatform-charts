package com.netguru.charts.barchart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp

const val BAR_MAX_WIDTH = 20f
const val BAR_MAX_HORIZONTAL_SPACING = 10f

internal fun DrawScope.drawBarChart(
    data: BarChartData,
    barsInCategoryMaxWidth: Float,
    verticalPadding: Dp,
    valueScale: Float
) {
    val canvasHeight = size.height
    val verticalPaddingInPx = verticalPadding.toPx()

    val barsInACategoryCount = data.categories.maxOfOrNull { it.entries.size } ?: return
    val barWidth = (barsInCategoryMaxWidth / barsInACategoryCount)
        .coerceAtMost(BAR_MAX_WIDTH)

    data.categories.forEachIndexed { categoryIndex, category ->
        val barsInCategoryCount = category.entries.size
        val barsHorizontalSpacing = BAR_MAX_HORIZONTAL_SPACING / barsInACategoryCount
        val barsInCategoryWidth = barsInCategoryCount * barWidth +
            (barsInCategoryCount - 1) * barsHorizontalSpacing

        category.entries.forEachIndexed { entryIndex, entry ->
            val barHeight = (entry.y / data.maxY * (canvasHeight - 2 * verticalPaddingInPx) * valueScale)
                .coerceAtLeast(0f)
            drawRect(
                color = entry.color,
                topLeft = Offset(
                    x = (categoryIndex * 2 + 1) * barsInCategoryMaxWidth +
                        entryIndex * (barWidth + barsHorizontalSpacing) -
                        barsInCategoryWidth / 2,
                    y = canvasHeight - verticalPaddingInPx - barHeight
                ),
                size = Size(
                    width = barWidth,
                    height = barHeight
                ),
            )
        }
    }
}
