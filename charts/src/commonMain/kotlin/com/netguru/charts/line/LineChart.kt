package com.netguru.charts.line

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.netguru.charts.gridchart.AXIS_FONT_SIZE
import com.netguru.charts.gridchart.LineParameters
import com.netguru.charts.gridchart.YAxisLabels
import com.netguru.charts.gridchart.axisscale.NiceTicksAxisScale
import com.netguru.charts.gridchart.determineGridLinesPositions
import com.netguru.charts.gridchart.drawChartGrid
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

private val HORIZONTAL_LINES_OFFSET = 10.dp
private const val NUMBER_OF_GRID_LINES = 5 // number of grid lines
val dashedPathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)

private const val DEFAULT_ANIMATION_DURATION_MS = 300
private const val DEFAULT_ANIMATION_DELAY_MS = 100
private const val ALPHA_MAX = 1f
private const val ALPHA_MIN = 0f

@Composable
fun LineChart(
    lineChartData: LineChartData,
    xAxisValueFormatter: (Long) -> String,
    timeFormatter: (Long) -> String,
    modifier: Modifier = Modifier,
    horizontalLinesOffset: Dp = HORIZONTAL_LINES_OFFSET,
    maxVerticalLines: Int = NUMBER_OF_GRID_LINES,
    maxHorizontalLines: Int = NUMBER_OF_GRID_LINES,
    animate: Boolean = true,
    chartColors: ChartColors = ChartDefaults.chartColors(),
    typography: Typography = MaterialTheme.typography,
) {
    var touchEvent by remember { mutableStateOf(PointerEvent(emptyList())) }
    var verticalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }
    var horizontalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }

    var animationPlayed by remember(animate) {
        mutableStateOf(!animate)
    }
    val alpha by animateFloatAsState(
        targetValue = if (animationPlayed) ALPHA_MAX else ALPHA_MIN,
        animationSpec = tween(
            durationMillis = DEFAULT_ANIMATION_DURATION_MS,
            delayMillis = DEFAULT_ANIMATION_DELAY_MS
        )
    )
    val gridColor = chartColors.grid

    LaunchedEffect(key1 = true) {
        animationPlayed = true // to play animation only once
    }

    Row(modifier = modifier) {
        YAxisLabels(
            horizontalGridLines = horizontalGridLines,
            unit = lineChartData.units,
            decimalPlaces = 1,
            labelsColor = chartColors.labels,
        )

        Spacer(modifier = Modifier.size(4.dp, 0.dp))

        // main chart
        Column(Modifier.fillMaxSize()) {
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .drawBehind {
                        val lines = determineGridLinesPositions(
                            xAxisScale = NiceTicksAxisScale(
                                min = lineChartData.minX.toFloat(),
                                max = lineChartData.maxX.toFloat(),
                                maxTickCount = maxVerticalLines - 1
                            ),
                            yAxisScale = NiceTicksAxisScale(
                                min = lineChartData.minY,
                                max = lineChartData.maxY,
                                maxTickCount = maxHorizontalLines - 1
                            ),
                            horizontalLinesOffset = horizontalLinesOffset
                        )
                        verticalGridLines = lines.verticalLines
                        horizontalGridLines = lines.horizontalLines
                        drawChartGrid(lines, gridColor)

                        drawLineChart(
                            lineChartData = lineChartData,
                            graphTopPadding = horizontalLinesOffset,
                            graphBottomPadding = horizontalLinesOffset, alpha
                        )
                    }
                    // Touch input
                    .pointerInput(Unit) {
                        while (true) {
                            val event = awaitPointerEventScope { awaitPointerEvent() }
                            touchEvent = event
                        }
                    }
            ) {
                // Overlay
                OverlayInformation(
                    lineChartData = lineChartData,
                    pointerEvent = touchEvent,
                    containerSize = with(LocalDensity.current) { Size(maxWidth.toPx(), maxHeight.toPx()) },
                    chartColors = chartColors,
                    timeFormatter = timeFormatter,
                    typography = typography,
                )
            }

            // x axis labels
            Box(Modifier.fillMaxWidth()) {
                for (gridLine in verticalGridLines) {
                    val labelXOffset = with(LocalDensity.current) { (gridLine.position).toDp() }
                    Text(
                        modifier = Modifier.offset(labelXOffset, 0.dp),
                        fontSize = AXIS_FONT_SIZE.sp,
                        color = chartColors.surface,
                        text = xAxisValueFormatter(gridLine.value.toLong())
                    )
                }
            }
        }
    }
}
