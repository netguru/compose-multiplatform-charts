package com.netguru.charts.gridchart.axisscale

class FixedTicksAxisScale(
    override val min: Float,
    override val max: Float,
    tickCount: Int
) : AxisScale {
    override val tick: Float = (max - min) / tickCount
}
