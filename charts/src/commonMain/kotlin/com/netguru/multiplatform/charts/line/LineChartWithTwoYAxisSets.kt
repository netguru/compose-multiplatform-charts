package com.netguru.multiplatform.charts.line

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.getAnimationAlphas
import com.netguru.multiplatform.charts.grid.ChartGridDefaults
import com.netguru.multiplatform.charts.grid.DrawXAxisMarkers
import com.netguru.multiplatform.charts.grid.LineParameters
import com.netguru.multiplatform.charts.grid.YAxisLabels
import com.netguru.multiplatform.charts.grid.YAxisTitleData
import com.netguru.multiplatform.charts.grid.axisscale.x.TimestampXAxisScale
import com.netguru.multiplatform.charts.grid.axisscale.y.YAxisScaleDynamic
import com.netguru.multiplatform.charts.grid.axisscale.y.YAxisScaleStatic
import com.netguru.multiplatform.charts.grid.drawChartGrid
import com.netguru.multiplatform.charts.grid.measureChartGrid

/**
 * Classic line chart with some shade below the line in the same color as the line (albeit with a lot of transparency)
 * and tooltip on touch/click to show values for that particular x-axis value but with the option to show two different
 * Y-axis scales (one on each side of the chart).
 *
 * Color, shape and whether the line is dashed for each of the lines is specified in the [LegendItemData] instance
 * inside each [LineChartData].
 *
 * @param leftYAxisData Data to display with scale on the left side
 * @param rightYAxisData Data to display with scale on the right side. If null, classic [LineChart] is used.
 * @param modifier Compose modifier
 * @param leftYAxisConfig Configuration for the left Y axis
 * @param rightYAxisConfig Configuration for the right Y axis
 * @param xAxisConfig Configuration for the X axis. If null, X axis is not displayed
 * @param legendConfig Config for the legend. If null, legend is not displayed
 * @param colors Colors used for grid, background, tooltip line color and tooltip background color
 * @param tooltipConfig Configuration for the tooltip. If null, tooltip is not shown
 * @param displayAnimation Animation to use to show the lines
 * @param shouldDrawValueDots Whether there should be a dot on the chart line for each non-null Y value
 * @param shouldInterpolateOverNullValues Whether chart line should be interpolated between two non-null Y values if
 * there is at least one null Y value between them. Setting to false interrupts the line and starts drawing at the next
 * non-null value
 */
@Composable
fun LineChartWithTwoYAxisSets(
    leftYAxisData: LineChartData,
    rightYAxisData: LineChartData?,
    modifier: Modifier = Modifier,
    leftYAxisConfig: YAxisConfig = YAxisConfig(),
    rightYAxisConfig: YAxisConfig = YAxisConfig(),
    xAxisConfig: XAxisConfig? = XAxisConfig(),
    legendConfig: LegendConfig? = LegendConfig(),
    colors: LineChartColors = LineChartDefaults.lineChartColors(),
    tooltipConfig: TooltipConfig? = TooltipConfig(),
    displayAnimation: ChartDisplayAnimation = ChartDisplayAnimation.Simple(),
    shouldDrawValueDots: Boolean = false,
    shouldInterpolateOverNullValues: Boolean = true,
) {
    if (rightYAxisData != null) {
        LineChartWithTwoYAxisSetsLayout(
            leftYAxisData = leftYAxisData,
            rightYAxisData = rightYAxisData,
            modifier = modifier,
            leftYAxisConfig = leftYAxisConfig,
            rightYAxisConfig = rightYAxisConfig,
            xAxisConfig = xAxisConfig,
            legendConfig = legendConfig,
            colors = colors,
            tooltipConfig = tooltipConfig,
            displayAnimation = displayAnimation,
            shouldDrawValueDots = shouldDrawValueDots,
            shouldInterpolateOverNullValues = shouldInterpolateOverNullValues,
        )
    } else {
        LineChart(
            data = leftYAxisData,
            modifier = modifier,
            yAxisConfig = leftYAxisConfig,
            xAxisConfig = xAxisConfig,
            legendConfig = legendConfig,
            colors = colors,
            tooltipConfig = tooltipConfig,
            displayAnimation = displayAnimation,
            shouldDrawValueDots = shouldDrawValueDots,
            shouldInterpolateOverNullValues = shouldInterpolateOverNullValues,
        )
    }
}

@Composable
private fun LineChartWithTwoYAxisSetsLayout(
    leftYAxisData: LineChartData,
    rightYAxisData: LineChartData,
    modifier: Modifier,
    leftYAxisConfig: YAxisConfig,
    rightYAxisConfig: YAxisConfig,
    xAxisConfig: XAxisConfig?,
    legendConfig: LegendConfig?,
    colors: LineChartColors,
    tooltipConfig: TooltipConfig?,
    displayAnimation: ChartDisplayAnimation,
    shouldDrawValueDots: Boolean,
    shouldInterpolateOverNullValues: Boolean,
) {
    var touchPositionX by remember { mutableStateOf(-1f) }
    var verticalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }
    var leftYAxisMarks by remember { mutableStateOf(emptyList<LineParameters>()) }
    var rightYAxisMarks by remember { mutableStateOf(emptyList<LineParameters>()) }

    val alphas = getAnimationAlphas(
        animation = displayAnimation,
        numberOfElementsToAnimate = leftYAxisData.series.size + rightYAxisData.series.size,
        uniqueDatasetKey = LineChartData(
            series = leftYAxisData.series + rightYAxisData.series,
            dataUnit = null,
        ),
    )

    fun LineChartData.addNoYValuePointsFrom(another: LineChartData): LineChartData {
        val anotherSeries = another.series
            .map { it.copy(listOfPoints = it.listOfPoints.map { point -> point.copy(y = null) }) }

        return copy(series = series + anotherSeries)
    }

    Column(
        modifier = modifier,
    ) {
        if (leftYAxisConfig.yAxisTitleData?.labelPosition == YAxisTitleData.LabelPosition.Top ||
            rightYAxisConfig.yAxisTitleData?.labelPosition == YAxisTitleData.LabelPosition.Top
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                leftYAxisConfig.yAxisTitleData?.labelLayout?.invoke() ?: Spacer(Modifier.size(1.dp))
                rightYAxisConfig.yAxisTitleData?.labelLayout?.invoke() ?: Spacer(Modifier.size(1.dp))
            }
        }
        Row(modifier = Modifier.weight(1f)) {
            if (leftYAxisConfig.markerLayout != null) {
                YAxisLabels(
                    horizontalGridLines = leftYAxisMarks,
                    yAxisMarkerLayout = leftYAxisConfig.markerLayout,
                    yAxisTitleData = leftYAxisConfig.yAxisTitleData,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
            }

            val numberOfXAxisEntries by remember(leftYAxisData, rightYAxisData) {
                derivedStateOf {
                    (leftYAxisData.series +
                        rightYAxisData.series
                        )
                        .map {
                            it.listOfPoints
                        }
                        .maxOf {
                            it.size
                        }
                }
            }

            // main chart
            Column(Modifier.weight(1f)) {
                var pointsToDraw: List<SeriesAndClosestPoint> by remember {
                    mutableStateOf(emptyList())
                }
                val xAxisScale = TimestampXAxisScale(
                    min = minOf(leftYAxisData.minX, rightYAxisData.minX),
                    max = maxOf(leftYAxisData.maxX, rightYAxisData.maxX),
                    maxTicksCount = (
                        minOf(
                            xAxisConfig?.maxVerticalLines ?: ChartGridDefaults.NUMBER_OF_GRID_LINES,
                            numberOfXAxisEntries
                        ) - 1
                        )
                        .coerceAtLeast(1),
                    roundClosestTo = xAxisConfig?.roundMinMaxClosestTo
                )
                BoxWithConstraints(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .drawBehind {
                            val lines = measureChartGrid(
                                xAxisScale = xAxisScale,
                                yAxisScale = YAxisScaleStatic(
                                    min = 0f,
                                    max = leftYAxisConfig.maxHorizontalLines.toFloat(),
                                    maxTickCount = leftYAxisConfig.maxHorizontalLines - 1,
                                    roundClosestTo = 1f,
                                ),
                            ).also {
                                verticalGridLines = it.verticalLines
                            }

                            leftYAxisMarks = measureChartGrid(
                                xAxisScale = TimestampXAxisScale(
                                    min = 0,
                                    max = 0,
                                    roundClosestTo = xAxisConfig?.roundMinMaxClosestTo,
                                ),
                                yAxisScale = YAxisScaleDynamic(
                                    min = leftYAxisData.minY,
                                    max = leftYAxisData.maxY,
                                    maxTickCount = leftYAxisConfig.maxHorizontalLines - 1,
                                    roundClosestTo = leftYAxisConfig.roundMinMaxClosestTo,
                                )
                            )
                                .horizontalLines
                                .let {
                                    val containsZeroValue =
                                        it.firstOrNull { line -> line.position == lines.zeroPosition.position } != null
                                    if (containsZeroValue) {
                                        it
                                    } else {
                                        it + lines.zeroPosition
                                    }
                                }
                            rightYAxisMarks = measureChartGrid(
                                xAxisScale = TimestampXAxisScale(
                                    min = 0,
                                    max = 0,
                                    roundClosestTo = xAxisConfig?.roundMinMaxClosestTo,
                                ),
                                yAxisScale = YAxisScaleDynamic(
                                    min = rightYAxisData.minY,
                                    max = rightYAxisData.maxY,
                                    maxTickCount = rightYAxisConfig.maxHorizontalLines - 1,
                                    roundClosestTo = rightYAxisConfig.roundMinMaxClosestTo,
                                )
                            )
                                .horizontalLines
                                .let {
                                    val containsZeroValue =
                                        it.firstOrNull { line -> line.position == lines.zeroPosition.position } != null
                                    if (containsZeroValue) {
                                        it
                                    } else {
                                        it + lines.zeroPosition
                                    }
                                }

                            drawChartGrid(lines, colors.grid)

                            drawLineChart(
                                // we have to join those points so that the x-values align properly. Otherwise, in case when
                                // datasets would not start and end at the same x value, they would still be drawn from the
                                // same start and end point, making (at least) one of them drawn incorrectly
                                lineChartData = leftYAxisData.addNoYValuePointsFrom(rightYAxisData),
                                alpha = alphas,
                                drawPoints = shouldDrawValueDots,
                                selectedPointsForDrawing = pointsToDraw.filter {
                                    leftYAxisData.series.contains(
                                        it.lineChartSeries
                                    )
                                },
                                xAxisScale = xAxisScale,
                                shouldInterpolateOverNullValues = shouldInterpolateOverNullValues,
                            )

                            drawLineChart(
                                // we have to join those points so that the x-values align properly. Otherwise, in case when
                                // datasets would not start and end at the same x value, they would still be drawn from the
                                // same start and end point, making (at least) one of them drawn incorrectly
                                lineChartData = rightYAxisData.addNoYValuePointsFrom(leftYAxisData),
                                alpha = alphas,
                                drawPoints = shouldDrawValueDots,
                                selectedPointsForDrawing = pointsToDraw.filter {
                                    rightYAxisData.series.contains(
                                        it.lineChartSeries
                                    )
                                },
                                xAxisScale = xAxisScale,
                                shouldInterpolateOverNullValues = shouldInterpolateOverNullValues,
                            )
                        }
                        // Touch input
                        .pointerInput(Unit) {
                            while (true) {
                                awaitPointerEventScope {
                                    val event = awaitPointerEvent(pass = PointerEventPass.Initial)

                                    touchPositionX = if (
                                        shouldIgnoreTouchInput(
                                            event = event,
                                            containerSize = size
                                        )
                                    ) {
                                        -1f
                                    } else {
                                        event.changes[0].position.x
                                    }

                                    event.changes.any {
                                        it.consume()
                                        true
                                    }
                                }
                            }
                        },
                    content = {
                        // Overlay
                        if (tooltipConfig != null) {
                            LineChartTooltip(
                                lineChartData = listOf(leftYAxisData, rightYAxisData),
                                positionX = touchPositionX,
                                containerSize = with(LocalDensity.current) {
                                    Size(
                                        maxWidth.toPx(),
                                        maxHeight.toPx()
                                    )
                                },
                                colors = colors,
                                drawPoints = {
                                    pointsToDraw = it
                                },
                                tooltipConfig = tooltipConfig,
                                xAxisScale = xAxisScale,
                            )
                        }
                    }
                )

                if (xAxisConfig != null) {
                    Box(Modifier.fillMaxWidth()) {
                        DrawXAxisMarkers(
                            lineParams = verticalGridLines,
                            xAxisConfig = xAxisConfig,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }

            if (rightYAxisConfig.markerLayout != null) {
                YAxisLabels(
                    horizontalGridLines = rightYAxisMarks,
                    yAxisMarkerLayout = rightYAxisConfig.markerLayout,
                    yAxisTitleData = rightYAxisConfig.yAxisTitleData,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
        }

        if (legendConfig != null) {
            ChartLegend(
                legendData = leftYAxisData.legendData + rightYAxisData.legendData,
                animation = displayAnimation,
                legendItemLabel = legendConfig.legendItemLabel,
                columnMinWidth = legendConfig.columnMinWidth,
            )
        }
    }
}

private fun shouldIgnoreTouchInput(event: PointerEvent, containerSize: IntSize): Boolean {
    if (event.changes.isEmpty() ||
        event.type != PointerEventType.Move
    ) {
        return true
    }
    if (event.changes[0].position.x < 0 ||
        event.changes[0].position.x > containerSize.width
    ) {
        return true
    }
    if (event.changes[0].position.y < 0 ||
        event.changes[0].position.y > containerSize.height
    ) {
        return true
    }
    return false
}
