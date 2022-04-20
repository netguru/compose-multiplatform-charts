package com.netguru.charts.barchart

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netguru.charts.line.ChartLegend
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

@Composable
fun BarChartWithLegend(
    data: BarChartData,
    unit: String,
    modifier: Modifier = Modifier,
    animate: Boolean = false,
    chartColors: ChartColors = ChartDefaults.chartColors(),
) {
    Column(modifier) {
        BarChart(
            modifier = Modifier.weight(1f),
            data = data,
            unit = unit,
            animate = animate,
            chartColors = chartColors,
        )
        ChartLegend(
            legendData = data.legendData,
            animate = animate,
            textColor = chartColors.labels,
        )
    }
}
