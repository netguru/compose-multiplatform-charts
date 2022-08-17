package com.netguru.multiplatform.charts.pie

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

/**
 * The customization parameters for [PieChart]
 *
 * @param thickness Width of the arc
 * @param legendPadding The space between chart and legend in both orientations
 * @param legendIcon The type of icon in legend [LegendIcon]
 * @param legendIconSize Size of the icon in legend
 * @param legendOrientation This parameters determinate the place where the legend will be drawn.
 * [LegendOrientation.HORIZONTAL] will draw all legend items under the chart in grid with number
 * of columns provided by [numberOfColsInLegend].
 * [LegendOrientation.VERTICAL] will draw all legend items on the right side in column.
 * @param numberOfColsInLegend Determinate number of legend items per row in
 * [LegendOrientation.HORIZONTAL] orientation.
 */
@Immutable
data class PieChartConfig(
    val thickness: Dp = PieDefaults.THICKNESS,
    val legendPadding: Dp = PieDefaults.LEGEND_PADDING,
    val legendIcon: LegendIcon = LegendIcon.CAKE,
    val legendIconSize: Dp = PieDefaults.LEGEND_ICON_SIZE,
    val legendOrientation: LegendOrientation = LegendOrientation.HORIZONTAL,
    val numberOfColsInLegend: Int = PieDefaults.NUMBER_OF_COLS_IN_LEGEND,
)

enum class LegendIcon {
    SQUARE,
    CIRCLE,
    ROUND,
    CAKE,
}

enum class LegendOrientation {
    HORIZONTAL,
    VERTICAL,
}
