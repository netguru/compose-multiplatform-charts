package com.netguru.charts.pie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.netguru.charts.ChartAnimation

/**
 * Version of [PieChart] with legend.
 *
 * @param legendItemLabel Composable to use to represent the item in the legend
 *
 * @see PieChart
 */
@Composable
fun PieChartWithLegend(
    pieChartData: List<PieChartData>,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    config: PieChartConfig = PieChartConfig(),
    legendItemLabel: @Composable (PieChartData) -> Unit = PieDefaults.LegendItemLabel,
) {
    when (config.legendOrientation) {
        LegendOrientation.HORIZONTAL ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(config.legendPadding),
                modifier = modifier,
            ) {
                PieChart(
                    data = pieChartData,
                    modifier = Modifier.weight(1f),
                    animation = animation,
                    config = config,
                )
                PieChartLegend(
                    data = pieChartData,
                    animation = animation,
                    legendItemLabel = legendItemLabel,
                    config = config,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        LegendOrientation.VERTICAL ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(config.legendPadding),
                modifier = modifier,
            ) {
                PieChart(
                    data = pieChartData,
                    modifier = Modifier.fillMaxHeight(),
                    animation = animation,
                    config = config,
                )
                PieChartLegend(
                    data = pieChartData,
                    animation = animation,
                    legendItemLabel = legendItemLabel,
                    config = config,
                    modifier = Modifier.wrapContentWidth(),
                )
            }
    }
}
