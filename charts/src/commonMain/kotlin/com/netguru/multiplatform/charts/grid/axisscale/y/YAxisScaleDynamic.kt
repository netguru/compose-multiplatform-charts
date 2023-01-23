package com.netguru.multiplatform.charts.grid.axisscale.y

import com.netguru.multiplatform.charts.grid.ChartGridDefaults
import com.netguru.multiplatform.charts.grid.GridChartData
import com.netguru.multiplatform.charts.roundToMultiplicationOf
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sign

class YAxisScaleDynamic(
    chartData: GridChartData,
    maxNumberOfHorizontalLines: Int = ChartGridDefaults.NUMBER_OF_GRID_LINES,
    roundMarkersToMultiplicationOf: Float? = ChartGridDefaults.ROUND_Y_AXIS_MARKERS_CLOSEST_TO,
    forceShowingValueZeroLine: Boolean = false,
) : YAxisScale {
    override val step: Float
    override val min: Float
    override val max: Float
    override val numberOfHorizontalLines: Int


    private fun roundToMultiplicationOf(numberToRound: Float, multiplicandBase: Float, ceiling: Boolean): Float {
        var moveDecimalPointBy = 0
        if (multiplicandBase < 1) {
            var tmp = multiplicandBase
            while (tmp.rem(1) > 0) {
                tmp *= 10
                moveDecimalPointBy++
            }
        }

        return numberToRound
            .times(10f.pow(moveDecimalPointBy))
            .roundToMultiplicationOf(
                multiplicand = multiplicandBase
                    .times(10f.pow(moveDecimalPointBy)),
                roundToCeiling = ceiling,
            )
            .div(10f.pow(moveDecimalPointBy))
    }

    init {
        if (maxNumberOfHorizontalLines <= 0) {
            throw IllegalArgumentException("maxNumberOfHorizontalLines must be positive")
        }
        if (roundMarkersToMultiplicationOf != null && roundMarkersToMultiplicationOf <= 0) {
            throw IllegalArgumentException("roundMarkersToMultiplicationOf must be either null or a positive number")
        }
        val validMin = if (chartData.minY.isNaN()) {
            0f
        } else {
            chartData.minY
        }
        val validMax = if (chartData.maxY.isNaN()) {
            0f
        } else {
            chartData.maxY
        }

        this.step = if (maxNumberOfHorizontalLines == 1) {
            0f
        } else {
            val maxDiffToShowLinesFor = if (validMax.sign == validMin.sign) {
                validMax - validMin
            } else {
                max(validMax, -validMin)
            }
            (maxDiffToShowLinesFor / maxNumberOfHorizontalLines)
                .let {
                    if (roundMarkersToMultiplicationOf == null) {
                        it
                    } else {
                        roundToMultiplicationOf(it, roundMarkersToMultiplicationOf, true)
                    }
                }
        }

        this.min = run {
            val roundedMin = if (step == 0f) {
                if (roundMarkersToMultiplicationOf == null) {
                    // do not round
                    validMin
                } else {
                    roundToMultiplicationOf(validMin, roundMarkersToMultiplicationOf, false)
                }
            } else {
                roundToMultiplicationOf(validMin, step, false)
            }

            if (roundedMin > 0 && forceShowingValueZeroLine) {
                0f
            } else {
                roundedMin
            }
        }

        this.max = run {
            val roundedMax = if (step == 0f) {
                if (roundMarkersToMultiplicationOf == null) {
                    // do not round
                    validMax
                } else {
                    roundToMultiplicationOf(validMax, roundMarkersToMultiplicationOf, true)
                }
            } else {
                roundToMultiplicationOf(validMax, step, true)
            }

            if (roundedMax < 0 && forceShowingValueZeroLine) {
                0f
            } else {
                roundedMax
            }
        }

        this.numberOfHorizontalLines = if (step == 0f) {
            if (min.sign == max.sign) {
                1
            } else {
                2
            }
        } else {
            if (min.sign == max.sign) {
                ((max - min) / step).roundToInt()
            } else {
                (max / step).roundToInt() + (-min / step).roundToInt()
            }
        }
    }

    override fun toString(): String {
        return "min: $min, max: $max, step: $step"
    }
}
