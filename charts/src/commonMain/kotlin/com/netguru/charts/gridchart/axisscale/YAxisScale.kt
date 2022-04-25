package com.netguru.charts.gridchart.axisscale

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class YAxisScale(min: Float, max: Float, maxTickCount: Int) {
    val tick: Float
    val min: Float
    val max: Float

    init {
        val range = niceNum(max - min, false)
        this.tick = niceNum(range / (maxTickCount), true)

        this.min = floor(min / tick) * tick
        this.max = ceil(max / tick) * tick
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
