package com.netguru.charts.grid.axisscale

class FixedTicksXAxisScale(
    override val min: Long,
    override val max: Long,
    tickCount: Int
) : XAxisScale {
    override val tick: Long = (max - min) / tickCount
    override val start: Long = min
}
