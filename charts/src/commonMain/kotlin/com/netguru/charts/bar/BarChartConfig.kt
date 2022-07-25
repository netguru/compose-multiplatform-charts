package com.netguru.charts.bar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

/**
 * The customization parameters for [BarChart]
 *
 * @param thickness Width of a single bar
 * @param cornerRadius 0 for square bars, thickness/2 for fully rounded corners
 * @param barsSpacing The space between bars in a cluster
 */
@Immutable
data class BarChartConfig(
    val thickness: Dp = BarChartDefaults.BAR_THICKNESS,
    val cornerRadius: Dp = BarChartDefaults.BAR_CORNER_RADIUS,
    val barsSpacing: Dp = BarChartDefaults.BAR_HORIZONTAL_SPACING,
)
