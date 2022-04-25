package com.netguru.charts.gridchart.axisscale

class TimestampXAxisScale(
    override val min: Long,
    override val max: Long,
    val maxTicksCount: Int = 10
) : XAxisScale {
    // find first round hour, greater than min timestamp
    override val start: Long = min - min % HOUR_MS + HOUR_MS

    // find period of vertical lines based on maxTicksCount, period can be in round hours ie. 1h, 2h, 3h
    private val period: Long = HOUR_MS * ((max - min) / HOUR_MS / maxTicksCount)

    // if period is 0 or less set period to round hour
    // avoid division by 0 when app starts and min and max are 0
    override val tick: Long = if (period > 0) period else HOUR_MS

    companion object {
        const val HOUR_MS = 3600000L
    }
}
