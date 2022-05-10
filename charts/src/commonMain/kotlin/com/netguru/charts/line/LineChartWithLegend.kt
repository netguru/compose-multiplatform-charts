package com.netguru.charts.line

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.netguru.charts.gridchart.GridDefaults
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

@Composable
fun LineChartWithLegend(
    lineChartData: LineChartData,
    modifier: Modifier,
    horizontalLinesOffset: Dp = GridDefaults.HORIZONTAL_LINES_OFFSET,
    maxVerticalLines: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    maxHorizontalLines: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    animate: Boolean,
    chartColors: ChartColors = ChartDefaults.chartColors(),
    xAxisMarkerLayout: @Composable (value: Any) -> Unit = GridDefaults.DefaultXAxisMarkerLayout,
    yAxisMarkerLayout: @Composable (value: Any) -> Unit = GridDefaults.DefaultYAxisMarkerLayout,
    overlayHeaderLayout: @Composable (value: Any) -> Unit = GridDefaults.DefaultOverlayHeaderLayout,
    overlayDataEntryLayout: @Composable (dataName: String, value: Any) -> Unit = GridDefaults.DefaultOverlayDataEntryLayout,
    legendItemLabel: @Composable (String) -> Unit = GridDefaults.DefaultLegendItemLabel,
) {
    Column(modifier = modifier) {
        LineChart(
            lineChartData = lineChartData,
            modifier = Modifier.weight(1f),
            horizontalLinesOffset = horizontalLinesOffset,
            maxVerticalLines = maxVerticalLines,
            maxHorizontalLines = maxHorizontalLines,
            animate = animate,
            chartColors = chartColors,
            xAxisMarkerLayout = xAxisMarkerLayout,
            yAxisMarkerLayout = yAxisMarkerLayout,
            overlayHeaderLayout = overlayHeaderLayout,
            overlayDataEntryLayout = overlayDataEntryLayout,
        )
        ChartLegend(
            legendData = lineChartData.legendData,
            animate = animate,
            legendItemLabel = legendItemLabel,
        )
    }
}
