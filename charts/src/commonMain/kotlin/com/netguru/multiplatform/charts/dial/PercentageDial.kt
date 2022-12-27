package com.netguru.multiplatform.charts.dial

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.theme.ChartTheme

/**
 * Variant of [Dial] with min value set to 0 and max value set to 100.
 *
 * @param percentage Value to show
 *
 * @see Dial
 */
@Composable
fun PercentageDial(
    percentage: Int,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    colors: DialColors = ChartTheme.colors.dialColors,
    config: DialConfig = DialConfig(),
    minAndMaxValueLabel: @Composable (value: Int) -> Unit = DialDefaults.MinAndMaxValueLabel,
    mainLabel: @Composable (value: Int) -> Unit = DialDefaults.MainLabel,
    indicator: (@Composable () -> Unit)? = null,
) {
    Dial(
        value = percentage,
        minValue = 0,
        maxValue = 100,
        modifier = modifier,
        animation = animation,
        colors = colors,
        config = config,
        minAndMaxValueLabel = minAndMaxValueLabel,
        mainLabel = mainLabel,
        indicator = indicator,
    )
}
