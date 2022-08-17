package com.netguru.multiplatform.charts.bar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.grid.GridDefaults
import com.netguru.multiplatform.charts.line.ChartLegend
import com.netguru.multiplatform.charts.theme.ChartTheme

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
    colors: BarChartColors = ChartTheme.colors.barChartColors,
    config: BarChartConfig = BarChartConfig(),
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
            colors = colors,
            config = config,
            xAxisLabel = xAxisLabel,
            yAxisLabel = yAxisLabel,
        )
        ChartLegend(
            legendData = data.legendData,
            animation = animation,
            config = config,
            legendItemLabel = legendItemLabel,
        )
    }
}
