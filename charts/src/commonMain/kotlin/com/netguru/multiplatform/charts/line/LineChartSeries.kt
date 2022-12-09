package com.netguru.multiplatform.charts.line

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.grid.GridChartData

@Immutable
data class LineChartPoint(
    val x: Long,
    val y: Float?,
)

@Immutable
data class LineChartSeries(
    val dataName: String,
    val lineWidth: Dp = 3.dp,
    val lineColor: Color,
    val fillColor: Color = lineColor,
    val pathEffect: PathEffect? = null,
    val listOfPoints: List<LineChartPoint> = emptyList(),
) {
    val minValue: Float
    val maxValue: Float
    val minTimestamp: Long
    val maxTimestamp: Long

    init {
        // find max and min in series
        if (listOfPoints.isNotEmpty()) {
            val minMaxValue = getMinMaxValue()
            minValue = minMaxValue.first
            maxValue = minMaxValue.second

            val minMaxTimestamp = getMinMaxTimestamp()
            minTimestamp = minMaxTimestamp.first
            maxTimestamp = minMaxTimestamp.second
        } else {
            minValue = 0f
            maxValue = 0f
            minTimestamp = 0L
            maxTimestamp = 0L
        }
    }

    private fun getMinMaxTimestamp(): Pair<Long, Long> {
        val sortedTimestamp = listOfPoints.sortedBy { it.x }
        return Pair(sortedTimestamp.first().x, sortedTimestamp.last().x)
    }

    private fun getMinMaxValue(): Pair<Float, Float> {
        return listOfPoints
            .filter { it.y != null }
            .sortedBy { it.y }
            .takeIf { it.isNotEmpty() }
            ?.let {
                Pair(it.first().y!!, it.last().y!!)
            } ?: Pair(0f, 0f)
    }
}

@Immutable
data class LineChartData(
    val series: List<LineChartSeries>,
    val dataUnit: String?,
) : GridChartData {
    override val legendData: List<LegendItemData>
        get() = series.map {
            LegendItemData(
                name = it.dataName,
                unit = dataUnit,
                symbolShape = SymbolShape.LINE,
                color = it.lineColor,
                pathEffect = it.pathEffect,
            )
        }

    override val minX: Long
    override val maxX: Long
    override val minY: Float
    override val maxY: Float

    init {
        // find max and min in all data
        val timeStamps = mutableListOf<Long>()
        val values = mutableListOf<Float>()
        series
            .forEach {
                if (it.listOfPoints.any { point -> point.y != null }) {
                    // null-only series, that are used to make timestamp ranges compatible between different series,
                    // must not be used for y-axis values, since they always report min=0f, which breaks the chart.
                    values.add(it.minValue)
                    values.add(it.maxValue)
                }
                timeStamps.add(it.minTimestamp)
                timeStamps.add(it.maxTimestamp)
            }
        minX = timeStamps.minOrNull() ?: 0
        maxX = timeStamps.maxOrNull() ?: 0
        minY = values.minOrNull() ?: 0f
        maxY = values.maxOrNull() ?: 0f
    }
}
