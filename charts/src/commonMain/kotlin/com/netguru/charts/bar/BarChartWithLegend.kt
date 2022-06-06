package com.netguru.charts.bar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netguru.charts.ChartAnimation
import com.netguru.charts.grid.GridDefaults
import com.netguru.charts.line.ChartLegend
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

/**
 * This bar chart shows data organised in categories together with a legend.
 *
 * @param legendItemLabel Composable to show for each item in the legend. Square representing the
 * color of the item, drawn to the left of it, is not customizable.
 *
 * @see BarChart
 */
@Composable
fun BarChartWithLegend(
    data: BarChartData,
    modifier: Modifier = Modifier,
    maxHorizontalLinesCount: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    animation: ChartAnimation = ChartAnimation.Simple(),
    chartColors: ChartColors = ChartDefaults.chartColors(),
    xAxisLabel: @Composable (value: Any) -> Unit = GridDefaults.XAxisLabel,
    yAxisLabel: @Composable (value: Any) -> Unit = GridDefaults.YAxisLabel,
    legendItemLabel: @Composable (String) -> Unit = GridDefaults.LegendItemLabel,
) {
    Column(modifier) {
        BarChart(
            modifier = Modifier.weight(1f),
            data = data,
            maxHorizontalLinesCount = maxHorizontalLinesCount,
            animation = animation,
            chartColors = chartColors,
            xAxisLabel = xAxisLabel,
            yAxisLabel = yAxisLabel,
        )
        ChartLegend(
            legendData = data.legendData,
            animation = animation,
            legendItemLabel = legendItemLabel,
        )
    }
}
