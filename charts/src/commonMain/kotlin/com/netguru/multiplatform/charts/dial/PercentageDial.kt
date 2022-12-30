package com.netguru.multiplatform.charts.dial

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.dial.scale.Scale
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
    percentage: Float,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    colors: DialColors = ChartTheme.colors.dialColors,
    config: DialConfig = DialConfig(),
    minAndMaxValueLabel: (@Composable (value: Float) -> Unit)? = DialDefaults.MinAndMaxValueLabel,
    mainLabel: @Composable (value: Float) -> Unit = DialDefaults.MainLabel,
    indicator: (@Composable () -> Unit)? = null,
    scale: Scale = Scale.Linear(null),
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
        scale = scale,
    )
}
