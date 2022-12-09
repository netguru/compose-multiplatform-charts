package com.netguru.multiplatform.charts.grid

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.netguru.multiplatform.charts.grid.axisscale.x.XAxisScale
import com.netguru.multiplatform.charts.grid.axisscale.y.YAxisScale
import com.netguru.multiplatform.charts.mapValueToDifferentRange

fun DrawScope.drawChartGrid(
    grid: ChartGrid,
    color: Color,
) {
    grid.horizontalLines.forEach {
        drawLine(
            color = color,
            start = Offset(0f, it.position),
            end = Offset(size.width, it.position),
            strokeWidth = 1f,
        )
    }
    drawLine(
        color = color,
        start = Offset(0f, grid.zeroPosition.position),
        end = Offset(size.width, grid.zeroPosition.position),
        strokeWidth = 1f,
    )
    grid.verticalLines.forEach {
        drawLine(
            color = color,
            start = Offset(it.position, 0f),
            end = Offset(it.position, size.height),
            strokeWidth = 1f,
        )
    }
}

fun DrawScope.measureChartGrid(
    xAxisScale: XAxisScale,
    yAxisScale: YAxisScale,
): ChartGrid {

    val horizontalLines = measureHorizontalLines(
        axisScale = yAxisScale,
        startPosition = size.height,
        endPosition = 0f
    )

    val verticalLines = measureVerticalLines(
        axisScale = xAxisScale,
        startPosition = 0f,
        endPosition = size.width
    )

    val zero = when {
        yAxisScale.min == yAxisScale.max -> 0f
        yAxisScale.min > 0 -> yAxisScale.min
        yAxisScale.max < 0 -> yAxisScale.max
        else -> 0f
    }
    return ChartGrid(
        verticalLines = verticalLines,
        horizontalLines = horizontalLines,
        zeroPosition = LineParameters(
            zero.mapValueToDifferentRange(
                if (yAxisScale.min == yAxisScale.max) 0f else yAxisScale.min,
                yAxisScale.max,
                size.height,
                0f
            ),
            zero
        )
    )
}

private fun measureHorizontalLines(
    axisScale: YAxisScale,
    startPosition: Float,
    endPosition: Float
): List<LineParameters> {
    val horizontalLines = mutableListOf<LineParameters>()

    if (axisScale.max == axisScale.min || axisScale.tick == 0f) {
        return listOf(
            LineParameters(
                position = axisScale.max.mapValueToDifferentRange(
                    0f,
                    axisScale.max,
                    startPosition,
                    endPosition
                ),
                value = axisScale.max,
            ),
        )
    }

    val valueStep = axisScale.tick
    var currentValue = axisScale.min

    while (currentValue in axisScale.min..axisScale.max) {
        val currentPosition = currentValue.mapValueToDifferentRange(
            axisScale.min,
            axisScale.max,
            startPosition,
            endPosition,
        )
        horizontalLines.add(
            LineParameters(
                position = currentPosition,
                value = currentValue
            )
        )
        currentValue += valueStep
    }
    return horizontalLines
}

fun measureVerticalLines(
    axisScale: XAxisScale,
    startPosition: Float,
    endPosition: Float
): List<LineParameters> {
    val verticalLines = mutableListOf<LineParameters>()
    val valueStep = axisScale.tick.coerceAtLeast(1)
    var currentValue = axisScale.start

    while (currentValue in axisScale.min..axisScale.max) {
        val currentPosition = currentValue.mapValueToDifferentRange(
            axisScale.min,
            axisScale.max,
            startPosition,
            endPosition
        )
        verticalLines.add(
            LineParameters(
                position = currentPosition,
                value = currentValue
            )
        )
        currentValue += valueStep
    }
    return verticalLines
}
