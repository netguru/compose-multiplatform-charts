package com.netguru.multiplatform.charts.line

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.isOutOfBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.getAnimationAlphas
import com.netguru.multiplatform.charts.grid.DrawXAxisMarkers
import com.netguru.multiplatform.charts.grid.ChartGridDefaults
import com.netguru.multiplatform.charts.grid.LineParameters
import com.netguru.multiplatform.charts.grid.YAxisLabels
import com.netguru.multiplatform.charts.grid.YAxisTitleData
import com.netguru.multiplatform.charts.grid.axisscale.x.TimestampXAxisScale
import com.netguru.multiplatform.charts.grid.drawChartGrid
import com.netguru.multiplatform.charts.grid.measureChartGrid
import com.netguru.multiplatform.charts.theme.ChartTheme

/**
 * Classic line chart with some shade below the line in the same color as the line (albeit with a lot of transparency)
 * and tooltip on touch/click to show values for that particular x-axis value.
 *
 * Color, shape and whether the line is dashed for each of the lines is specified in the [LegendItemData] instance
 * inside [LineChartData].
 *
 * @param data Data to display
 * @param modifier Compose modifier
 * @param yAxisConfig Configuration for the Y axis
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
fun LineChart(
    data: LineChartData,
    modifier: Modifier = Modifier,
    yAxisConfig: YAxisConfig = ChartGridDefaults.yAxisConfig(data),
    xAxisConfig: XAxisConfig? = XAxisConfig(),
    legendConfig: LegendConfig? = LegendConfig(),
    colors: LineChartColors = LineChartDefaults.lineChartColors(),
    tooltipConfig: TooltipConfig? = TooltipConfig(),
    displayAnimation: ChartDisplayAnimation = ChartDisplayAnimation.Simple(),
    shouldDrawValueDots: Boolean = false,
    shouldInterpolateOverNullValues: Boolean = true,
) {
    var touchPositionX by remember { mutableStateOf(-1f) }
    var verticalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }
    var horizontalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }

    val alpha = getAnimationAlphas(displayAnimation, data.series.size, data)

    Column(
        modifier = modifier
    ) {
        if (yAxisConfig.yAxisTitleData?.labelPosition == YAxisTitleData.LabelPosition.Top) {
            yAxisConfig.yAxisTitleData.labelLayout()
        }
        Row(modifier = Modifier.weight(1f)) {
            if (yAxisConfig.markerLayout != null) {
                YAxisLabels(
                    horizontalGridLines = horizontalGridLines,
                    yAxisMarkerLayout = yAxisConfig.markerLayout,
                    yAxisTitleData = yAxisConfig.yAxisTitleData,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
            }

            val numberOfXAxisEntries by remember(data) {
                derivedStateOf {
                    data
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
                    min = data.minX,
                    max = data.maxX,
                    maxTicksCount = (
                        minOf(
                            xAxisConfig?.maxVerticalLines ?: ChartGridDefaults.NUMBER_OF_GRID_LINES,
                            numberOfXAxisEntries
                        ) - 1
                        )
                        .coerceAtLeast(1),
                    roundClosestTo = xAxisConfig?.roundMarkersToMultiplicationOf
                        ?: ChartGridDefaults.ROUND_X_AXIS_MARKERS_CLOSEST_TO,
                )
                BoxWithConstraints(
                    modifier = Modifier
                        .background(colors.surface)
                        .fillMaxWidth()
                        .weight(1f)
                        .drawBehind {
                            val lines = measureChartGrid(
                                xAxisScale = xAxisScale,
                                yAxisScale = yAxisConfig.scale,
                            )
                            verticalGridLines = lines.verticalLines
                            horizontalGridLines = lines.horizontalLines
                            drawChartGrid(
                                grid = lines,
                                color = colors.grid,
                            )

                            drawLineChart(
                                xAxisScale = xAxisScale,
                                yAxisScale = yAxisConfig.scale,
                                lineChartData = data,
                                alpha = alpha,
                                drawDots = shouldDrawValueDots,
                                selectedPointsForDrawing = pointsToDraw,
                                shouldInterpolateOverNullValues = shouldInterpolateOverNullValues,
                            )
                        }
                        .then(
                            if (tooltipConfig != null) {
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
                        if (tooltipConfig != null) {
                            LineChartTooltip(
                                lineChartData = listOf(data),
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
        }

        if (legendConfig != null) {
            ChartLegend(
                legendData = data.legendData,
                animation = displayAnimation,
                legendItemLabel = legendConfig.legendItemLabel,
                columnMinWidth = legendConfig.columnMinWidth,
            )
        }
    }
}

private fun shouldIgnoreTouchInput(event: PointerEvent): Boolean {
    if (
        event.changes.isEmpty() ||
        (event.type != PointerEventType.Move && event.type != PointerEventType.Press)
    ) {
        return true
    }

    return false
}

object LineChartDefaults {
    @Composable
    fun lineChartColors(
        backgroundColor: Color = ChartTheme.colors.surface,
        gridColor: Color = ChartTheme.colors.grid,
        overlayLineColor: Color = ChartTheme.colors.overlayLine,
        overlayBackgroundColor: Color = ChartTheme.colors.surface,
    ): LineChartColors = LineChartColors(
        surface = backgroundColor,
        grid = gridColor,
        overlayLine = overlayLineColor,
        overlaySurface = overlayBackgroundColor,
    )
}
