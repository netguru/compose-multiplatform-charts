package com.netguru.multiplatform.charts.line

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import com.netguru.multiplatform.charts.grid.axisscale.x.TimestampXAxisScale
import com.netguru.multiplatform.charts.grid.axisscale.y.YAxisScale
import com.netguru.multiplatform.charts.mapValueToDifferentRange

internal fun DrawScope.drawLineChart(
    lineChartData: LineChartData,
    alpha: List<Float>,
    drawDots: Boolean,
    selectedPointsForDrawing: List<SeriesAndClosestPoint>,
    xAxisScale: TimestampXAxisScale,
    yAxisScale: YAxisScale,
    shouldInterpolateOverNullValues: Boolean,
) {
    // calculate path
    val path = Path()
    lineChartData.series.forEachIndexed { seriesIndex, unfilteredData ->

        val filteredLists: MutableList<List<LineChartPoint>> = mutableListOf()
        if (shouldInterpolateOverNullValues) {
            filteredLists.add(unfilteredData.listOfPoints.filter { it.y != null })
        } else {
            var tempList: MutableList<LineChartPoint> = mutableListOf()
            unfilteredData.listOfPoints.forEach { lineChartPoint ->
                if (lineChartPoint.y != null) {
                    tempList.add(lineChartPoint)
                } else {
                    if (tempList.isNotEmpty()) {
                        filteredLists.add(tempList)
                        tempList = mutableListOf()
                    }
                }
            }
            if (tempList.isNotEmpty()) {
                filteredLists.add(tempList)
                tempList = mutableListOf()
            }
        }

        val (shouldSetZeroAsMinValue, shouldSetZeroAsMaxValue) = unfilteredData
            .listOfPoints
            .filter { it.y != null }
            .distinctBy { it.y }
            .takeIf { it.size == 1 }
            ?.let { (it.first().y!! > 0) to (it.first().y!! < 0) }
            ?: (false to false)

        filteredLists
            .map { unfilteredData.copy(listOfPoints = it) }
            .forEach { data ->
                if (data.listOfPoints.isEmpty()) {
                    return@forEachIndexed
                }
                val mappedPoints =
                    mapDataToPixels(
                        xAxisScale = xAxisScale,
                        yAxisScale = yAxisScale,
                        currentSeries = data,
                        canvasSize = size,
                        shouldSetZeroAsMinValue = shouldSetZeroAsMinValue,
                        shouldSetZeroAsMaxValue = shouldSetZeroAsMaxValue,
                    )
                val connectionPoints = calculateConnectionPointsForBezierCurve(mappedPoints)

                path.reset() // reuse path
                mappedPoints
                    .filter { it.y != null }
                    .forEachIndexed { index, value ->
                        if (index == 0) {
                            path.moveTo(value.x, value.y!!)
                        } else {
                            val point = connectionPoints[index - 1]
                            path.cubicTo(
                                x1 = point.first.x,
                                y1 = point.first.y!!,
                                x2 = point.second.x,
                                y2 = point.second.y!!,
                                x3 = value.x,
                                y3 = value.y!!,
                            )
                        }
                    }

                if (mappedPoints.size == 1 || drawDots) {
                    val pointSize = data.lineWidth.toPx().times(if (drawDots) 3f else 2f)
                    val widthThePointsTake = mappedPoints.maxOf { it.x } - mappedPoints.minOf { it.x }
                    val isEnoughSpace =
                        (mappedPoints.size - 2 /* this 2 is a magic number, it just works better with it */) * pointSize * 1.5 < widthThePointsTake
                    if (isEnoughSpace) {
                        drawPoints(
                            points = mappedPoints.filter { it.y != null }.map { Offset(it.x, it.y!!) },
                            pointMode = PointMode.Points,
                            color = data.lineColor,
                            alpha = alpha[seriesIndex],
                            strokeWidth = pointSize,
                            cap = StrokeCap.Round,
                        )
                    }
                }

                if (mappedPoints.size > 1) {
                    // draw line
                    drawPath(
                        path = path,
                        color = data.lineColor.copy(alpha[seriesIndex]),
                        style = Stroke(
                            width = data.lineWidth.toPx(),
                            pathEffect = data.pathEffect,
                        ),
                    )
                }

                // close shape and fill
                path.lineTo(mappedPoints.last().x, size.height)
                path.lineTo(mappedPoints.first().x, size.height)
                drawPath(
                    path = path,
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            data.fillColor.copy(alpha[seriesIndex] / 12),
                            data.fillColor.copy(alpha[seriesIndex] / 6)
                        ),
                        startY = path.getBounds().bottom,
                        endY = path.getBounds().top,
                    ),
                    style = Fill,
                )

                if (selectedPointsForDrawing.isNotEmpty()) {
                    val offsets = selectedPointsForDrawing
                        .map { seriesAndClosestPoint ->
                            val x = seriesAndClosestPoint.closestPoint.x.mapValueToDifferentRange(
                                xAxisScale.start,
                                xAxisScale.end,
                                0L,
                                size.width.toLong()
                            ).toFloat()
                            val y = seriesAndClosestPoint.closestPoint.y?.mapValueToDifferentRange(
                                if (shouldSetZeroAsMinValue) 0f else lineChartData.minY,
                                lineChartData.maxY,
                                size.height,
                                0f,
                            )
                            Offset(x, y!!)
                        }
                    drawPoints(
                        points = offsets,
                        pointMode = PointMode.Points,
                        color = data.lineColor,
                        alpha = alpha[seriesIndex],
                        strokeWidth = data.lineWidth.toPx().times(5f),
                        cap = StrokeCap.Round,
                    )
                }
            }
    }
}

private fun mapDataToPixels(
    currentSeries: LineChartSeries,
    canvasSize: Size,
    shouldSetZeroAsMinValue: Boolean,
    shouldSetZeroAsMaxValue: Boolean,
    xAxisScale: TimestampXAxisScale,
    yAxisScale: YAxisScale,
): List<PointF> {
    val mappedPoints = currentSeries.listOfPoints.map {
        val x = it.x.mapValueToDifferentRange(
            inMin = xAxisScale.start,
            inMax = xAxisScale.end,
            outMin = 0L,
            outMax = canvasSize.width.toLong()
        ).toFloat()
        val y = it.y?.mapValueToDifferentRange(
            inMin = if (shouldSetZeroAsMinValue) 0f else yAxisScale.min,
            inMax = if (shouldSetZeroAsMaxValue) 0f else yAxisScale.max,
            outMin = canvasSize.height,
            outMax = 0f,
        )
        PointF(x, y)
    }

    return mappedPoints
}

private fun calculateConnectionPointsForBezierCurve(points2: List<PointF>): MutableList<Pair<PointF, PointF>> {
    val conPoint = mutableListOf<Pair<PointF, PointF>>()
    val points = points2.filter { it.y != null }
    for (i in 1 until points.size) {
        conPoint.add(
            Pair(
                PointF((points[i].x + points[i - 1].x) / 2f, points[i - 1].y),
                PointF((points[i].x + points[i - 1].x) / 2f, points[i].y)
            )
        )
    }
    return conPoint
}

data class PointF(val x: Float, val y: Float?)
