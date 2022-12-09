package com.netguru.multiplatform.charts.grid.axisscale.x

class TimestampXAxisScale(
    override val min: Long,
    override val max: Long,
    val maxTicksCount: Int = 10
) : XAxisScale {
    // find first round hour, greater than min timestamp
    override val start: Long// = min - min % period + period

    // if period is 0 or less set period to round hour
    // avoid division by 0 when app starts and min and max are 0
    override val tick: Long// = if (period > 0) period else HOUR_MS

    // find period of vertical lines based on maxTicksCount, period can be in round hours i.e. 1h, 2h, 3h
//    private val tick: Long// = HOUR_MS * ((max - min) / HOUR_MS / maxTicksCount)

    init {
        val diff = (max - min).div(maxTicksCount)
        tick = when {
            diff > HOUR_MS -> HOUR_MS * ((max - min) / HOUR_MS / maxTicksCount)
            diff > MINUTE_MS -> MINUTE_MS * ((max - min) / MINUTE_MS / maxTicksCount)
            diff > SECOND_MS -> SECOND_MS * ((max - min) / SECOND_MS / maxTicksCount)
            else -> diff
        }

        start = /*if (tick > 0) min - min % tick + tick else*/ min
    }

    companion object {
        const val HOUR_MS = 3_600_000L
        const val MINUTE_MS = 60_000L
        const val SECOND_MS = 1_000L
    }
}
