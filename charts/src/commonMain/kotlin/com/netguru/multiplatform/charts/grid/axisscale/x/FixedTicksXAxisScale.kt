package com.netguru.multiplatform.charts.grid.axisscale.x

class FixedTicksXAxisScale(
    override val min: Long,
    override val max: Long,
    tickCount: Int
) : XAxisScale {
    override val tick: Long = (max - min) / tickCount
    override val start: Long = min
    override val end: Long = max
}
