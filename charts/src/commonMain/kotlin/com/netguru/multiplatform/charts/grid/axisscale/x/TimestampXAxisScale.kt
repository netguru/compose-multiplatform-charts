package com.netguru.multiplatform.charts.grid.axisscale.x

class TimestampXAxisScale(
    override val min: Long,
    override val max: Long,
    val maxTicksCount: Int = 10,
    roundClosestTo: Long?,
) : XAxisScale {

    override val start: Long = if (roundClosestTo != null) {
        (min / roundClosestTo) * roundClosestTo
    } else {
        min
    }

    override val tick: Long

    override val end: Long

    init {
        end = if (roundClosestTo != null) {
            ((max / roundClosestTo) * roundClosestTo) + roundClosestTo
        } else {
            max
        }

        val exactTick = (end - start) / maxTicksCount
        tick = if (roundClosestTo != null) {
            (exactTick / roundClosestTo) * roundClosestTo
        } else {
            exactTick
        }
    }
}
