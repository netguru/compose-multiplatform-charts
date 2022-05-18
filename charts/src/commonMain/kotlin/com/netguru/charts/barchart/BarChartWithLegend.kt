package com.netguru.charts.barchart

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netguru.charts.ChartAnimation
import com.netguru.charts.gridchart.GridDefaults
import com.netguru.charts.line.ChartLegend
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

@Composable
fun BarChartWithLegend(
    data: BarChartData,
    modifier: Modifier = Modifier,
    maxHorizontalLinesCount: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    animation: ChartAnimation = ChartAnimation.Disabled,
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
