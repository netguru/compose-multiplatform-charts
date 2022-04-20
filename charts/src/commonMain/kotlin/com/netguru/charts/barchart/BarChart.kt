package com.netguru.charts.barchart

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.netguru.charts.gridchart.AXIS_FONT_SIZE
import com.netguru.charts.gridchart.LineParameters
import com.netguru.charts.gridchart.YAxisLabels
import com.netguru.charts.gridchart.axisscale.FixedTicksAxisScale
import com.netguru.charts.gridchart.axisscale.NiceTicksAxisScale
import com.netguru.charts.gridchart.determineGridLinesPositions
import com.netguru.charts.gridchart.drawChartGrid
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
    val verticalLinesCount = remember(data) { data.maxX.toInt().coerceAtLeast(2) }

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
                val grid = determineGridLinesPositions(
                    xAxisScale = FixedTicksAxisScale(
                        min = data.minX.toFloat(),
                        max = data.maxX.toFloat(),
                        tickCount = verticalLinesCount - 1
                    ),
                    yAxisScale = NiceTicksAxisScale(
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
                        modifier = Modifier.offset(labelXOffset, 0.dp),
                        fontSize = AXIS_FONT_SIZE.sp,
                        color = labelsColor,
                        text = labels.getOrElse(i / 2) { "" },
                        textAlign = TextAlign.Center
                    )
                }
            }
    }
}
