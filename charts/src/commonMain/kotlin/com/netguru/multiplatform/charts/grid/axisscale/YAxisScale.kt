package com.netguru.multiplatform.charts.grid.axisscale

import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class YAxisScale(
    min: Float,
    max: Float,
    maxTickCount: Int,
    roundClosestTo: Int,
) {
    val tick: Float
    val min: Float
    val max: Float

    init {
        this.min = if (!min.isNaN()) {
            min.getClosest(roundClosestTo)
        } else {
            0f
        }
        this.max = if (!max.isNaN()) {
            max.getClosest(roundClosestTo)
        } else {
            0f
        }

        val range = niceNum(this.max - this.min, false)
        this.tick = niceNum(range / (maxTickCount), true)
    }

    private fun Float.getClosest(n: Int) = when {
        this > 0f -> (((this.toInt() + n - 1) / n) * n).toFloat()
        this < 0f -> (((this.toInt() - n + 1) / n) * n).toFloat()
        else -> 0f
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
}
