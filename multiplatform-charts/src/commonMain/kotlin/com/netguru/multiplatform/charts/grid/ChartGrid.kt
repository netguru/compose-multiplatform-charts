package com.netguru.multiplatform.charts.grid

import androidx.compose.runtime.Immutable
import kotlin.math.abs

@Immutable
data class ChartGrid(
    val verticalLines: List<LineParameters>,
    val horizontalLines: List<LineParameters>,
    val zeroPosition: LineParameters,
) {
    val distanceBetweenVerticalLines: Float
        get() = abs(verticalLines[1].position - verticalLines[0].position)
}
