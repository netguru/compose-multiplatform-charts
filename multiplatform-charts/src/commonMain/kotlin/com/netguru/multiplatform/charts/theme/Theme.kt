package com.netguru.multiplatform.charts.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalChartColors = staticCompositionLocalOf {
    ChartColors(
        primary = Color.Unspecified,
        surface = Color.Unspecified,
        grid = Color.Unspecified,
        emptyGasBottle = Color.Unspecified,
        fullGasBottle = Color.Unspecified,
        overlayLine = Color.Unspecified,
    )
}

internal object ChartTheme {
    val colors: ChartColors
        @Composable
        get() = LocalChartColors.current
}
