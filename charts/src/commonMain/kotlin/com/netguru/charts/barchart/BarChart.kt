package com.netguru.charts.barchart

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.netguru.charts.ChartAnimation
import com.netguru.charts.StartAnimation
import com.netguru.charts.gridchart.GridDefaults
import com.netguru.charts.gridchart.LineParameters
import com.netguru.charts.gridchart.YAxisLabels
import com.netguru.charts.gridchart.alignCenterToOffsetHorizontal
import com.netguru.charts.gridchart.axisscale.FixedTicksXAxisScale
import com.netguru.charts.gridchart.axisscale.YAxisScale
import com.netguru.charts.gridchart.drawChartGrid
import com.netguru.charts.gridchart.measureChartGrid
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

@Composable
fun BarChart(
    data: BarChartData,
    modifier: Modifier = Modifier,
    chartColors: ChartColors = ChartDefaults.chartColors(),
    xAxisLabel: @Composable (value: Any) -> Unit = GridDefaults.XAxisLabel,
    yAxisLabel: @Composable (value: Any) -> Unit = GridDefaults.YAxisLabel,
    animation: ChartAnimation = ChartAnimation.Simple(),
    maxHorizontalLinesCount: Int = GridDefaults.NUMBER_OF_GRID_LINES,
) {
    val verticalLinesCount = remember(data) { data.maxX.toInt() + 1 }
    val horizontalLinesOffset =
        GridDefaults.HORIZONTAL_LINES_OFFSET // TODO check why y-axis-labels get the other way around with large values for offset

    val animationPlayed = StartAnimation(animation, data)

    var verticalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }
    var horizontalGridLines by remember { mutableStateOf(emptyList<LineParameters>()) }

    val valueScale = when (animation) {
        ChartAnimation.Disabled -> data.categories.first().entries.indices.map { 1f }
        is ChartAnimation.Simple -> data.categories.first().entries.indices.map {
            animateFloatAsState(
                targetValue = if (animationPlayed) 1f else 0f,
                animationSpec = animation.animationSpec()
            ).value
        }
        is ChartAnimation.Sequenced -> data.categories.first().entries.indices.map {
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
                    yAxisUpperValue = grid.horizontalLines.last().value.toFloat(),
                    yAxisLowerValue = grid.horizontalLines.first().value.toFloat(),
                    verticalPadding = horizontalLinesOffset.toPx(),
                    valueScale = valueScale,
                )
            }

            XAxisLabels(
                labels = data.categories.map { it.name },
                verticalGridLines = verticalGridLines,
                xAxisMarkerLayout = xAxisLabel,
            )
        }
    }
}

@Composable
private fun XAxisLabels(
    labels: List<String>,
    verticalGridLines: List<LineParameters>,
    xAxisMarkerLayout: @Composable (value: String) -> Unit,
) {
    Box(Modifier.fillMaxWidth()) {
        verticalGridLines
            .dropLast(1)
            .forEachIndexed { i, verticalLine ->
                if (i % 2 == 1) {
                    Box(
                        modifier = Modifier
                            .alignCenterToOffsetHorizontal(verticalLine.position)
                    ) {
                        xAxisMarkerLayout(
                            labels.getOrElse(i / 2) { "" }
                        )
                    }
                }
            }
    }
}
