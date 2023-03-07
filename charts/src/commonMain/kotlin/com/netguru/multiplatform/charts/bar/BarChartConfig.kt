package com.netguru.multiplatform.charts.bar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.netguru.multiplatform.charts.grid.GridDefaults

/**
 * The customization parameters for [BarChart]
 *
 * @param thickness Width of a single bar
 * @param cornerRadius 0 for square bars, thickness/2 for fully rounded corners
 * @param barsSpacing The space between bars in a cluster
 * @param maxHorizontalLinesCount Max number of lines that are allowed to draw for marking y-axis
 * @param roundMinMaxClosestTo Number to which min and max range will be rounded to
 */
@Immutable
data class BarChartConfig(
    val thickness: Dp = BarChartDefaults.BAR_THICKNESS,
    val cornerRadius: Dp = BarChartDefaults.BAR_CORNER_RADIUS,
    val barsSpacing: Dp = BarChartDefaults.BAR_HORIZONTAL_SPACING,
    val maxHorizontalLinesCount: Int = GridDefaults.NUMBER_OF_GRID_LINES,
    val roundMinMaxClosestTo: Int = GridDefaults.ROUND_MIN_MAX_CLOSEST_TO,
)
