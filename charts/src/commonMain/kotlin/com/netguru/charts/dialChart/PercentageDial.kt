package com.netguru.charts.dialChart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netguru.charts.ChartAnimation
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults

@Composable
fun PercentageDial(
    percentage: Int,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    chartColors: ChartColors = ChartDefaults.chartColors(),
    minAndMaxValueLabel: @Composable (value: Int) -> Unit = DialDefaults.MinAndMaxValueLabel,
    mainLabel: @Composable (value: Int) -> Unit = DialDefaults.MainLabel,
) {
    Dial(
        value = percentage,
        minValue = 0,
        maxValue = 100,
        modifier = modifier,
        animation = animation,
        chartColors = chartColors,
        minAndMaxValueLabel = minAndMaxValueLabel,
        mainLabel = mainLabel,
    )
}
