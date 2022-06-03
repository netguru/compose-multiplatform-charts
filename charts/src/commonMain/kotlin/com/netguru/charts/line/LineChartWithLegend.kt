package com.netguru.charts.line

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netguru.charts.ChartAnimation
import com.netguru.charts.grid.GridDefaults
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

@Composable
fun LineChartWithLegend(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    maxVerticalLines: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    maxHorizontalLines: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    animation: ChartAnimation = ChartAnimation.Simple(),
    chartColors: ChartColors = ChartDefaults.chartColors(),
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
            chartColors = chartColors,
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
