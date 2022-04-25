package com.netguru.charts.barchart

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.netguru.charts.gridchart.*
import com.netguru.charts.gridchart.axisscale.FixedTicksXAxisScale
import com.netguru.charts.gridchart.axisscale.YAxisScale
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

@Composable
fun BarChart(
    data: BarChartData,
    unit: String,
    modifier: Modifier = Modifier,
    maxHorizontalLinesCount: Int = 5,
    horizontalLinesOffset: Dp = 10.dp,
    animate: Boolean = false,
    chartColors: ChartColors = ChartDefaults.chartColors(),
) {
    val verticalLinesCount = remember(data) { data.maxX.toInt() + 1 }

    var animationPlayed by remember { mutableStateOf(!animate) }
    val animationProgress by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(durationMillis = 600, delayMillis = 200)
    )
    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    var verticalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }
    var horizontalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }

    Row(modifier = modifier) {
        YAxisLabels(
            horizontalGridLines = horizontalGridLines,
            unit = unit,
            decimalPlaces = 2,
            labelsColor = chartColors.labels,
        )

        Spacer(modifier = Modifier.size(width = 4.dp, height = 0.dp))

        Column(modifier = Modifier.fillMaxSize()) {
            val gridColor = chartColors.grid
            Canvas(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                val grid = measureChartGrid(
                    xAxisScale = FixedTicksXAxisScale(
                        min = data.minX,
                        max = data.maxX,
                        tickCount = verticalLinesCount - 1
                    ),
                    yAxisScale = YAxisScale(
                        min = data.minY,
                        max = data.maxY,
                        maxTickCount = maxHorizontalLinesCount - 1
                    ),
                    horizontalLinesOffset = horizontalLinesOffset
                )
                verticalGridLines = grid.verticalLines
                horizontalGridLines = grid.horizontalLines

                drawChartGrid(grid, gridColor)
                drawBarChart(
                    data = data,
                    barsInCategoryMaxWidth = grid.distanceBetweenVerticalLines,
                    verticalPadding = horizontalLinesOffset,
                    valueScale = animationProgress
                )
            }

            XAxisLabels(
                labels = data.categories.map { it.name },
                verticalGridLines = verticalGridLines,
                labelsColor = chartColors.labels,
            )
        }
    }
}

@Composable
private fun XAxisLabels(
    labels: List<String>,
    verticalGridLines: List<LineParameters>,
    labelsColor: Color,
) {
    Box(Modifier.fillMaxWidth()) {
        verticalGridLines
            .dropLast(1)
            .forEachIndexed { i, verticalLine ->
                if (i % 2 == 1) {
                    val labelXOffset = with(LocalDensity.current) { (verticalLine.position).toDp() }
                    Text(
                        modifier = Modifier
                            .width(100.dp)
                            .offset(labelXOffset - 50.dp, 0.dp),
                        fontSize = AXIS_FONT_SIZE.sp,
                        color = labelsColor,
                        text = labels.getOrElse(i / 2) { "" },
                        textAlign = TextAlign.Center
                    )
                }
            }
    }
}
