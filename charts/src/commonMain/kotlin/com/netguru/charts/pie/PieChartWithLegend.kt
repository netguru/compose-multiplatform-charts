package com.netguru.charts.pie

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

@Composable
fun PieChartWithLegend(
    pieChartData: List<PieChartData>,
    modifier: Modifier = Modifier,
    chartColors: ChartColors = ChartDefaults.chartColors(),
    typography: Typography = MaterialTheme.typography,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        PieChart(
            data = pieChartData,
            modifier = Modifier.weight(1f)
        )
        PieChartLegend(
            data = pieChartData,
            columns = 4,
            chartColors = chartColors,
            typography = typography,
        )
    }
}
