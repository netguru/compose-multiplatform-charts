package com.netguru.multiplatform.charts.grid.axisscale.y

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class YAxisScaleDynamic(
    min: Float,
    max: Float,
    maxTickCount: Int,
    roundClosestTo: Float,
) : YAxisScale {
    override val tick: Float
    override val min: Float
    override val max: Float

    init {

        if (roundClosestTo <= 0) {
            throw IllegalArgumentException("roundClosestTo must be a positive number")
        }

        var moveDecimalPointBy = 0
        var tempClosestTo = roundClosestTo
        if (roundClosestTo < 1) {
            while (tempClosestTo.rem(1) > 0) {
                tempClosestTo *= 10
                moveDecimalPointBy++
            }
        }

        this.min = if (min.isNaN()) {
            0f
        } else {
            min
                .times(10f.pow(moveDecimalPointBy))
                .getClosest(
                    n = roundClosestTo
                        .times(10f.pow(moveDecimalPointBy)),
                    ceil = false,
                )
                .div(10f.pow(moveDecimalPointBy))

        }
        this.max = if (max.isNaN()) {
            0f
        } else {
            max
                .times(10f.pow(moveDecimalPointBy))
                .getClosest(
                    n = roundClosestTo
                        .times(10f.pow(moveDecimalPointBy)),
                    ceil = true,
                )
                .div(10f.pow(moveDecimalPointBy))
        }

        val range = niceNum(this.max - this.min, false)
        this.tick = niceNum(range / (maxTickCount), true)
    }

    private fun Float.getClosest(n: Float, ceil: Boolean) = when {
        this > 0f -> ((this + n - 1) / n).ceilOrFloor(ceil) * n
        this < 0f -> ((this - n + 1) / n).ceilOrFloor(ceil) * n
        else -> 0f
    }

    private fun Float.ceilOrFloor(ceil: Boolean): Float = if (ceil) {
        ceil(this)
    } else {
        floor(this)
    }

    /**
     * Returns a "nice" number approximately equal to range.
     * Rounds the number if round = true Takes the ceiling if round = false.
     *
     * @param range the data range
     * @param round whether to round the result
     * @return a "nice" number to be used for the data range
     */
    private fun niceNum(range: Float, round: Boolean): Float {
        /** nice, rounded fraction  */
        val exponent: Float = floor(log10(range))

        /** exponent of range  */
        val fraction = range / 10.0f.pow(exponent)

        /** fractional part of range  */
        val niceFraction: Float = if (round) {
            if (fraction < 1.5) 1.0f else if (fraction < 3) 2.0f else if (fraction < 7) 5.0f else 10.0f
        } else {
            if (fraction <= 1) 1.0f else if (fraction <= 2) 2.0f else if (fraction <= 5) 5.0f else 10.0f
        }
        return niceFraction * 10.0f.pow(exponent)
    }

    override fun toString(): String {
        return "min: $min, max: $max, tick: $tick"
    }
}
