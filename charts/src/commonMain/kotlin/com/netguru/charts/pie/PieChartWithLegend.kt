package com.netguru.charts.pie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.netguru.charts.ChartAnimation

@Composable
fun PieChartWithLegend(
    pieChartData: List<PieChartData>,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    columns: Int = PieDefaults.NUMBER_OF_COLS_IN_LEGEND,
    legendItemLabel: @Composable (PieChartData) -> Unit = PieDefaults.LegendItemLabel,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        PieChart(
            data = pieChartData,
            modifier = Modifier.weight(1f),
            animation = animation,
        )
        PieChartLegend(
            data = pieChartData,
            columns = columns,
            animation = animation,
            legendItemLabel = legendItemLabel,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
