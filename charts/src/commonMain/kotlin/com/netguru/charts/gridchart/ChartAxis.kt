package com.netguru.charts.gridchart

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val AXIS_FONT_SIZE = 12

/**
 * @param horizontalGridLines positions and values of horizontal grid lines
 * @param unit String that is displayed next to the value on the Y axis
 * @param decimalPlaces number of decimal places to show on the Y axis values
 * @param labelsColor Should be its parent's instance of ChartDefaults.chartColors().labelsColor().value
 */
@Composable
internal fun YAxisLabels(
    horizontalGridLines: List<LineParameters>,
    unit: String,
    decimalPlaces: Int,
    labelsColor: Color,
) {
    Box(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .fillMaxHeight()
            .padding(end = 8.dp)
    ) {
        horizontalGridLines.forEach { horizontalLine ->
            val labelYOffset = with(LocalDensity.current) { (horizontalLine.position).toDp() }
            Text(
                modifier = Modifier
                    .offset(0.dp, labelYOffset - (AXIS_FONT_SIZE / 2).dp)
                    .fillMaxWidth(),
                fontSize = AXIS_FONT_SIZE.sp,
                color = labelsColor,
                text = horizontalLine.value.formatForYLabel(decimalPlaces) + " " + unit,
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }
    }
}

private fun Float.formatForYLabel(decimalPlaces: Int): String =
    if (this == 0f) "0" else "%.${decimalPlaces}f".format(this)
