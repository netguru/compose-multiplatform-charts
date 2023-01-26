package com.netguru.multiplatform.charts.dial

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.netguru.multiplatform.charts.theme.ChartColors

@Immutable
data class DialChartColors(
    val progressBarColor: DialProgressColors,
    val progressBarBackgroundColor: Color,
    val gridScaleColor: Color,
)

@Immutable
sealed class DialProgressColors {
    data class SingleColor(val color: Color) : DialProgressColors()
    data class GradientWithStops(val colorStops: List<Pair<Float, Color>>) : DialProgressColors()
    data class Gradient(val colors: List<Color>) : DialProgressColors()
}
