package com.netguru.multiplatform.charts.bar

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.grid.ChartGridDefaults
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
    animation: ChartDisplayAnimation = ChartDisplayAnimation.Simple(),
    colors: BarChartColors = ChartTheme.colors.barChartColors,
    config: BarChartConfig = BarChartConfig(),
    xAxisLabel: @Composable (value: Any) -> Unit = ChartGridDefaults.XAxisMarkerLayout,
    yAxisLabel: @Composable (value: Any) -> Unit = ChartGridDefaults.YAxisMarkerLayout,
    yAxisLabelLayout: (@Composable () -> Unit)? = null,
    legendItemLabel: @Composable (name: String, unit: String?) -> Unit = ChartGridDefaults.LegendItemLabel,
    columnMinWidth: Dp = 200.dp,
) {
    Column(modifier) {
        BarChart(
            modifier = Modifier.weight(1f),
            data = data,
            animation = animation,
            colors = colors,
            config = config,
            xAxisMarkerLayout = xAxisLabel,
            yAxisMarkerLayout = yAxisLabel,
            yAxisLabelLayout = yAxisLabelLayout,
        )
        ChartLegend(
            legendData = data.legendData,
            animation = animation,
            config = config,
            legendItemLabel = legendItemLabel,
            columnMinWidth = columnMinWidth,
        )
    }
}
