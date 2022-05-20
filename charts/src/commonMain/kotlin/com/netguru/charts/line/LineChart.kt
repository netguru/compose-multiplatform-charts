package com.netguru.charts.line

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.netguru.charts.ChartAnimation
import com.netguru.charts.gridchart.GridDefaults
import com.netguru.charts.gridchart.LineParameters
import com.netguru.charts.gridchart.YAxisLabels
import com.netguru.charts.gridchart.alignCenterToOffsetHorizontal
import com.netguru.charts.gridchart.axisscale.TimestampXAxisScale
import com.netguru.charts.gridchart.axisscale.YAxisScale
import com.netguru.charts.gridchart.drawChartGrid
import com.netguru.charts.gridchart.measureChartGrid
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

val dashedPathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LineChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    chartColors: ChartColors = ChartDefaults.chartColors(),
    xAxisLabel: @Composable (value: Any) -> Unit = GridDefaults.XAxisLabel,
    yAxisLabel: @Composable (value: Any) -> Unit = GridDefaults.YAxisLabel,
    overlayHeaderLabel: @Composable (value: Any) -> Unit = GridDefaults.OverlayHeaderLabel,
    overlayDataEntryLabel: @Composable (dataName: String, value: Any) -> Unit = GridDefaults.OverlayDataEntryLabel,
    animation: ChartAnimation = ChartAnimation.Disabled,
    maxVerticalLines: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    maxHorizontalLines: Int = GridDefaults.NUMBER_OF_GRID_LINES,
) {
    var touchPositionX by remember { mutableStateOf(-1f) }
    var verticalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }
    var horizontalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }
    val horizontalLinesOffset: Dp = GridDefaults.HORIZONTAL_LINES_OFFSET

    var animationPlayed by remember(lineChartData) {
        mutableStateOf(animation is ChartAnimation.Disabled)
    }
    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    val alpha = when (animation) {
        ChartAnimation.Disabled -> lineChartData.series.indices.map { 1f }
        is ChartAnimation.Simple -> lineChartData.series.indices.map {
            animateFloatAsState(
                targetValue = if (animationPlayed) 1f else 0f,
                animationSpec = animation.animationSpec()
            ).value
        }
        is ChartAnimation.Sequenced -> lineChartData.series.indices.map {
            animateFloatAsState(
                targetValue = if (animationPlayed) 1f else 0f,
                animationSpec = animation.animationSpec(it)
            ).value
        }
    }

    Row(modifier = modifier) {
        YAxisLabels(
            horizontalGridLines = horizontalGridLines,
            yAxisMarkerLayout = yAxisLabel,
        )

        Spacer(modifier = Modifier.size(4.dp, 0.dp))

        // main chart
        Column(Modifier.fillMaxSize()) {
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .drawBehind {
                        val lines = measureChartGrid(
                            xAxisScale = TimestampXAxisScale(
                                min = lineChartData.minX,
                                max = lineChartData.maxX,
                                maxTicksCount = maxVerticalLines - 1

                            ),
                            yAxisScale = YAxisScale(
                                min = lineChartData.minY,
                                max = lineChartData.maxY,
                                maxTickCount = maxHorizontalLines - 1
                            ),
                            horizontalLinesOffset = horizontalLinesOffset
                        )
                        verticalGridLines = lines.verticalLines
                        horizontalGridLines = lines.horizontalLines
                        drawChartGrid(lines, chartColors.grid)

                        drawLineChart(
                            lineChartData = lineChartData,
                            graphTopPadding = horizontalLinesOffset,
                            graphBottomPadding = horizontalLinesOffset,
                            alpha = alpha,
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
                    }
            ) {
                // Overlay
                OverlayInformation(
                    lineChartData = lineChartData,
                    positionX = touchPositionX,
                    containerSize = with(LocalDensity.current) {
                        Size(
                            maxWidth.toPx(),
                            maxHeight.toPx()
                        )
                    },
                    chartColors = chartColors,
                    overlayHeaderLayout = overlayHeaderLabel,
                    overlayDataEntryLayout = overlayDataEntryLabel,
                )
            }

            Box(Modifier.fillMaxWidth()) {
                for (gridLine in verticalGridLines) {
                    Box(
                        modifier = Modifier
                            .alignCenterToOffsetHorizontal(gridLine.position)
                    ) {
                        xAxisLabel(gridLine.value.toLong())
                    }
                }
            }
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
