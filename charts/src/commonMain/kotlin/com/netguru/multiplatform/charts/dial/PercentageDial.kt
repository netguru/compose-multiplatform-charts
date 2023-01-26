package com.netguru.multiplatform.charts.dial

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.line.Progression

/**
 * Variant of [Dial] with min value set to 0 and max value set to 100.
 *
 * @param percentage Value to show
 *
 * @see Dial
 */
@Deprecated(
    message = "The default Dial param values are the same as the ones for PercentageDial",
    replaceWith = ReplaceWith("Dial(value = percentage, modifier = modifier, animation = animation, colors = colors, config = config, minAndMaxValueLabel = minAndMaxValueLabel, mainLabel = mainLabel, indicator = indicator)")
)
@Composable
fun PercentageDial(
    percentage: Float,
    modifier: Modifier = Modifier,
    animation: ChartDisplayAnimation = ChartDisplayAnimation.Simple(),
    colors: DialChartColors = DialChartDefaults.dialChartColors(),
    config: DialConfig = DialConfig(),
    minAndMaxValueLabel: (@Composable (value: Float) -> Unit)? = DialDefaults.MinAndMaxValueLabel,
    mainLabel: @Composable (value: Float) -> Unit = DialDefaults.MainLabel,
    indicator: (@Composable () -> Unit)? = null,
) {
    Dial(
        value = percentage,
        minValue = 0f,
        maxValue = 100f,
        modifier = modifier,
        animation = animation,
        colors = colors,
        config = config,
        minAndMaxValueLabel = minAndMaxValueLabel,
        mainLabel = mainLabel,
        indicator = indicator,
        progression = Progression.Linear,
    )
}
