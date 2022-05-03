package com.netguru.charts.line

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

@Composable
fun LineChartWithLegend(
    modifier: Modifier,
    lineChartData: LineChartData,
    maxVerticalLines: Int,
    animate: Boolean,
    xAxisValueFormatter: (Long) -> String,
    timeFormatter: (Long) -> String,
    chartColors: ChartColors = ChartDefaults.chartColors(),
    typography: androidx.compose.material.Typography = MaterialTheme.typography,
) {
    Column(modifier = modifier) {
        LineChart(
            lineChartData = lineChartData,
            xAxisValueFormatter = xAxisValueFormatter,
            timeFormatter = timeFormatter,
            horizontalLinesOffset = 10.dp,
            animate = animate,
            modifier = Modifier.weight(1f),
            maxVerticalLines = maxVerticalLines,
            chartColors = chartColors,
            typography = typography,
        )
        ChartLegend(
            legendData = lineChartData.legendData,
            animate = animate,
            textColor = chartColors.labels,
        )
    }
}
