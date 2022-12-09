package com.netguru.multiplatform.charts.grid.axisscale.y

import kotlin.math.pow

class YAxisScaleStatic(
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
        }/* else if (roundClosestTo >= 10) {
            while (tempClosestTo >= 10) {
                tempClosestTo /= 10
                moveDecimalPointBy--
            }
        }*/

        this.min = if (min.isNaN()) {
            0f
        } else {
            min.getClosest(roundClosestTo)
//            min.times(10f.pow(moveDecimalPointBy)).getClosest(roundClosestTo.times(10f.pow(moveDecimalPointBy))).div(10f.pow(moveDecimalPointBy))
        }
        this.max = if (max.isNaN()) {
            0f
        } else {
            max.getClosest(roundClosestTo)
//            max.times(10f.pow(moveDecimalPointBy)).getClosest(roundClosestTo.times(10f.pow(moveDecimalPointBy))).div(10f.pow(moveDecimalPointBy))
        }

        val range = this.max - this.min
        this.tick = (range / maxTickCount)
    }

    private fun Float.getClosest(n: Float) = when {
        this > 0f -> ((this.toInt() + n - 1) / n) * n
        this < 0f -> ((this.toInt() - n + 1) / n) * n
        else -> 0f
    }

    override fun toString(): String {
        return "min: $min, max: $max, tick: $tick"
    }
}
