package com.netguru.multiplatform.charts.grid.axisscale.y

import com.netguru.multiplatform.charts.grid.ChartGridDefaults
import com.netguru.multiplatform.charts.grid.GridChartData

class YAxisScaleStatic(
    override val min: Float,
    override val max: Float,
    override val numberOfHorizontalLines: Int = ChartGridDefaults.NUMBER_OF_GRID_LINES,
) : YAxisScale {
    override val step: Float

    init {

        if (numberOfHorizontalLines <= 0) {
            throw IllegalArgumentException("numberOfHorizontalLines must be positive")
        }

        val range = this.max - this.min
        this.step = range / numberOfHorizontalLines
    }

    override fun toString(): String {
        return "min: $min, max: $max, step: $step"
    }
}
