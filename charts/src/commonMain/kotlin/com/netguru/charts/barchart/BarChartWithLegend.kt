package com.netguru.charts.barchart

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.netguru.charts.gridchart.GridDefaults
import com.netguru.charts.line.ChartLegend
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

@Composable
fun BarChartWithLegend(
    data: BarChartData,
    modifier: Modifier = Modifier,
    maxHorizontalLinesCount: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    horizontalLinesOffset: Dp = GridDefaults.HORIZONTAL_LINES_OFFSET,
    animate: Boolean = false,
    chartColors: ChartColors = ChartDefaults.chartColors(),
    xAxisMarkerLayout: @Composable (value: Any) -> Unit = GridDefaults.DefaultXAxisMarkerLayout,
    yAxisMarkerLayout: @Composable (value: Any) -> Unit = GridDefaults.DefaultYAxisMarkerLayout,
    legendItemLabel: @Composable (String) -> Unit = GridDefaults.DefaultLegendItemLabel,
) {
    Column(modifier) {
        BarChart(
            modifier = Modifier.weight(1f),
            data = data,
            maxHorizontalLinesCount = maxHorizontalLinesCount,
            horizontalLinesOffset = horizontalLinesOffset,
            animate = animate,
            chartColors = chartColors,
            xAxisMarkerLayout = xAxisMarkerLayout,
            yAxisMarkerLayout = yAxisMarkerLayout,
        )
        ChartLegend(
            legendData = data.legendData,
            animate = animate,
            legendItemLabel = legendItemLabel,
        )
    }
}
