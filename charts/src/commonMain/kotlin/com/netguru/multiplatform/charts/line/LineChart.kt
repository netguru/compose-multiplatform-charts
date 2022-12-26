package com.netguru.multiplatform.charts.line

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.isOutOfBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.getAnimationAlphas
import com.netguru.multiplatform.charts.grid.DrawXAxisMarkers
import com.netguru.multiplatform.charts.grid.GridDefaults
import com.netguru.multiplatform.charts.grid.LineParameters
import com.netguru.multiplatform.charts.grid.YAxisLabels
import com.netguru.multiplatform.charts.grid.YAxisTitleData
import com.netguru.multiplatform.charts.grid.axisscale.x.TimestampXAxisScale
import com.netguru.multiplatform.charts.grid.axisscale.y.YAxisScaleDynamic
import com.netguru.multiplatform.charts.grid.drawChartGrid
import com.netguru.multiplatform.charts.grid.measureChartGrid
import com.netguru.multiplatform.charts.theme.ChartColors
import com.netguru.multiplatform.charts.theme.ChartTheme

/**
 * Classic line chart with some shade below the line in the same color (albeit with a lot of
 * transparency) as the line and floating balloon on touch/click to show values for that particular
 * x-axis value.
 *
 * Color, shape and whether the line is dashed for each of the lines is specified in the
 * [LegendItemData] class.
 *
 * @param lineChartData Data to portray
 * @param colors Colors used are [ChartColors.grid], [ChartColors.surface] and
 * [ChartColors.overlayLine].
 * @param xAxisMarkerLayout Composable to mark the values on the x-axis.
 * @param yAxisMarkerLayout Composable to mark the values on the y-axis.
 * @param overlayHeaderLabel Composable to show the current x-axis value on the overlay balloon
 * @param overlayDataEntryLabel Composable to show the value of each line in the overlay balloon
 * for that specific x-axis value
 * @param animation Animation to use
 * @param maxVerticalLines Max number of lines, representing the x-axis values
 * @param maxHorizontalLines Max number of lines, representing the y-axis values
 * @param roundMinMaxClosestTo Number to which min and max range will be rounded to
 */
@Composable
fun LineChart(
    yAxisData: YAxisData,
    modifier: Modifier = Modifier,
    colors: LineChartColors = ChartTheme.colors.lineChartColors,
    overlayData: OverlayData? = OverlayData(),
    xAxisData: XAxisData? = XAxisData(),
    animation: ChartAnimation = ChartAnimation.Simple(),
    maxVerticalLines: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    maxHorizontalLines: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    drawPoints: Boolean = false,
    legendData: LegendData? = LegendData(),
) {
    var touchPositionX by remember { mutableStateOf(-1f) }
    var verticalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }
    var horizontalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }
    val horizontalLinesOffset: Dp = GridDefaults.HORIZONTAL_LINES_OFFSET

    val alpha = getAnimationAlphas(animation, yAxisData.lineChartData.series.size, yAxisData.lineChartData)

    Column(
        modifier = modifier
    ) {
        if (yAxisData.yAxisTitleData?.labelPosition == YAxisTitleData.LabelPosition.Top) {
            yAxisData.yAxisTitleData.labelLayout()
        }
        Row(modifier = Modifier.weight(1f)) {
            if (yAxisData.markerLayout != null) {
                YAxisLabels(
                    horizontalGridLines = horizontalGridLines,
                    yAxisMarkerLayout = yAxisData.markerLayout,
                    yAxisTitleData = yAxisData.yAxisTitleData,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
            }

            val numberOfXAxisEntries by remember(yAxisData.lineChartData) {
                derivedStateOf {
                    yAxisData
                        .lineChartData
                        .series
                        .map {
                            it.listOfPoints
                        }
                        .maxOf {
                            it.size
                        }
                }
            }

            // main chart
            Column(Modifier.fillMaxSize()) {
                var pointsToDraw: List<SeriesAndClosestPoint> by remember {
                    mutableStateOf(emptyList())
                }
                val xAxisScale = TimestampXAxisScale(
                    min = yAxisData.lineChartData.minX,
                    max = yAxisData.lineChartData.maxX,
                    maxTicksCount = (minOf(
                        maxVerticalLines, numberOfXAxisEntries
                    ) - 1).coerceAtLeast(1),
                    roundClosestTo = xAxisData?.roundMinMaxClosestTo,
                )
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .drawBehind {
                            val lines = measureChartGrid(
                                xAxisScale = xAxisScale,
                                yAxisScale = YAxisScaleDynamic(
                                    min = yAxisData.lineChartData.minY,
                                    max = yAxisData.lineChartData.maxY,
                                    maxTickCount = maxHorizontalLines - 1,
                                    roundClosestTo = yAxisData.roundMinMaxClosestTo,
                                ),
                            )
                            verticalGridLines = lines.verticalLines
                            horizontalGridLines = lines.horizontalLines + lines.zeroPosition
                            drawChartGrid(
                                grid = lines,
                                color = colors.grid,
                            )

                            drawLineChart(
                                xAxisScale = xAxisScale,
                                lineChartData = yAxisData.lineChartData,
                                graphTopPadding = horizontalLinesOffset,
                                graphBottomPadding = horizontalLinesOffset,
                                alpha = alpha,
                                drawPoints = drawPoints,
                                selectedPointsForDrawing = pointsToDraw,
                            )
                        }
                        .then(
                            if (overlayData != null) {
                                // Touch input
                                Modifier
                                    .pointerInput(Unit) {
                                        while (true) {
                                            awaitPointerEventScope {
                                                val event = awaitPointerEvent(pass = PointerEventPass.Initial)

                                                touchPositionX = if (
                                                    shouldIgnoreTouchInput(
                                                        event = event
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
                                    }
                                    .pointerInput(Unit) {
                                        while (true) {
                                            awaitPointerEventScope {
                                                val event = awaitPointerEvent(PointerEventPass.Main)
                                                if (
                                                    event.changes.all { it.changedToUp() } ||
                                                    event.changes.any {
                                                        it.isOutOfBounds(size, extendedTouchPadding)
                                                    }
                                                ) {
                                                    touchPositionX = -1f
                                                }
                                            }
                                        }
                                    }
                            } else {
                                Modifier
                            }
                        ),
                    content = {
                        // Overlay
                        if (overlayData != null) {
                            LineChartOverlayInformation(
                                lineChartData = listOf(yAxisData.lineChartData),
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
                                overlayData = overlayData,
                                xAxisScale = xAxisScale,
                            )
                        }
                    }
                )

                if (xAxisData != null) {
                    Box(Modifier.fillMaxWidth()) {
                        DrawXAxisMarkers(
                            lineParams = verticalGridLines,
                            xAxisData = xAxisData,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }

        if (legendData != null) {
            ChartLegend(
                legendData = yAxisData.lineChartData.legendData,
                animation = animation,
                legendItemLabel = legendData.legendItemLabel,
                columnMinWidth = legendData.columnMinWidth,
            )
        }
    }
}

private fun shouldIgnoreTouchInput(event: PointerEvent): Boolean {
    if (event.changes.isEmpty() ||
        (event.type != PointerEventType.Move && event.type != PointerEventType.Press)
    ) {
        return true
    }

    return false
}
