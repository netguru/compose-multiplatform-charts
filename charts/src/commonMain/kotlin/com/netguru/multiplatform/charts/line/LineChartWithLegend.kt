package com.netguru.multiplatform.charts.line

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.grid.GridDefaults
import com.netguru.multiplatform.charts.theme.ChartTheme

/**
 * Classic line chart with legend below the chart.
 *
 * For more information, check [LineChart].
 *
 * @param legendItemLabel Composable to use to represent the value in the legend. Only text value
 * is customizable.
 *
 * @see LineChart
 */
@Composable
fun LineChartWithLegend(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    maxVerticalLines: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    maxHorizontalLines: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    animation: ChartAnimation = ChartAnimation.Simple(),
    colors: LineChartColors = ChartTheme.colors.lineChartColors,
    xAxisLabel: @Composable (value: Any) -> Unit = GridDefaults.XAxisLabel,
    yAxisLabel: @Composable (value: Any) -> Unit = GridDefaults.YAxisLabel,
    overlayHeaderLabel: @Composable (value: Any) -> Unit = GridDefaults.OverlayHeaderLabel,
    overlayDataEntryLabel: @Composable (dataName: String, value: Any) -> Unit = GridDefaults.OverlayDataEntryLabel,
    legendItemLabel: @Composable (String) -> Unit = GridDefaults.LegendItemLabel,
) {
    Column(modifier = modifier) {
        LineChart(
            lineChartData = lineChartData,
            modifier = Modifier.weight(1f),
            maxVerticalLines = maxVerticalLines,
            maxHorizontalLines = maxHorizontalLines,
            animation = animation,
            colors = colors,
            xAxisLabel = xAxisLabel,
            yAxisLabel = yAxisLabel,
            overlayHeaderLabel = overlayHeaderLabel,
            overlayDataEntryLabel = overlayDataEntryLabel,
        )
        ChartLegend(
            legendData = lineChartData.legendData,
            animation = animation,
            legendItemLabel = legendItemLabel,
        )
    }
}
