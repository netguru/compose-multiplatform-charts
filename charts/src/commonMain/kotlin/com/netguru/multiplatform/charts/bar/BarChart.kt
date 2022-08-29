package com.netguru.multiplatform.charts.bar

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
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.StartAnimation
import com.netguru.multiplatform.charts.grid.GridDefaults
import com.netguru.multiplatform.charts.grid.LineParameters
import com.netguru.multiplatform.charts.grid.YAxisLabels
import com.netguru.multiplatform.charts.grid.alignCenterToOffsetHorizontal
import com.netguru.multiplatform.charts.grid.axisscale.FixedTicksXAxisScale
import com.netguru.multiplatform.charts.grid.axisscale.YAxisScale
import com.netguru.multiplatform.charts.grid.drawChartGrid
import com.netguru.multiplatform.charts.grid.measureChartGrid
import com.netguru.multiplatform.charts.theme.ChartColors
import com.netguru.multiplatform.charts.theme.ChartTheme

/**
 * This bar chart shows data organised in categories.
 *
 * @param data Data to show in the chart
 * @param colors The only parameter used is [ChartColors.grid]. Others play no role in
 * BarChart. Colors of the bars themselves are specified together with the data
 * @param config The parameters for chart appearance customization
 * @param xAxisLabel Composable to mark the values on the x-axis.
 * @param yAxisLabel Composable to mark the values on the y-axis.
 * @param animation In the case of [ChartAnimation.Sequenced] items with the same index in each
 * category will animate together
 * @param maxHorizontalLinesCount Max number of lines that are allowed to draw for marking y-axis
 * values
 */
@Composable
fun BarChart(
    data: BarChartData,
    modifier: Modifier = Modifier,
    colors: BarChartColors = ChartTheme.colors.barChartColors,
    config: BarChartConfig = BarChartConfig(),
    xAxisLabel: @Composable (value: Any) -> Unit = GridDefaults.XAxisLabel,
    yAxisLabel: @Composable (value: Any) -> Unit = GridDefaults.YAxisLabel,
    animation: ChartAnimation = ChartAnimation.Simple(),
    maxHorizontalLinesCount: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    roundMinMaxClosestTo: Int = GridDefaults.ROUND_MIN_MAX_CLOSEST_TO,
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
            val gridColor = colors.grid
            Canvas(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                val yAxisScale = YAxisScale(
                    min = data.minY,
                    max = data.maxY,
                    maxTickCount = maxHorizontalLinesCount,
                    roundClosestTo = roundMinMaxClosestTo,
                )
                val grid = measureChartGrid(
                    xAxisScale = FixedTicksXAxisScale(
                        min = data.minX,
                        max = data.maxX,
                        tickCount = verticalLinesCount - 1
                    ),
                    yAxisScale = yAxisScale,
                    horizontalLinesOffset = horizontalLinesOffset
                )
                verticalGridLines = grid.verticalLines
                horizontalGridLines = grid.horizontalLines

                drawChartGrid(grid, gridColor)
                drawBarChart(
                    data = data,
                    config = config,
                    yAxisUpperValue = yAxisScale.max,
                    yAxisLowerValue = yAxisScale.min,
                    valueScale = valueScale,
                    yAxisZeroPosition = grid.zeroPosition.position,
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
