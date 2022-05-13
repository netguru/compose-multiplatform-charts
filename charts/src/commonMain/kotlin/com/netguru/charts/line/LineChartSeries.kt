package com.netguru.charts.line

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.netguru.charts.gridchart.GridChartData

@Immutable
data class LineChartPoint(
    val xValue: Long,
    val yValue: Float,
)

@Immutable
data class LineChartSeries(
    val dataName: String,
    val lineWidth: Dp = 3.dp,
    val lineColor: Color,
    val fillColor: Color = lineColor,
    val dashedLine: Boolean = false,
    val listOfPoints: List<LineChartPoint> = emptyList(),
) {
    val minValue: Float
    val maxValue: Float
    val minTimestamp: Long
    val maxTimeStamp: Long

    init {
        // find max and min in series
        if (listOfPoints.isNotEmpty()) {
            val minMaxValue = getMinMaxValue()
            minValue = minMaxValue.first
            maxValue = minMaxValue.second

            val minMaxTimestamp = getMinMaxTimestamp()
            minTimestamp = minMaxTimestamp.first
            maxTimeStamp = minMaxTimestamp.second
        } else {
            minValue = 0f
            maxValue = 0f
            minTimestamp = 0L
            maxTimeStamp = 0L
        }
    }

    private fun getMinMaxTimestamp(): Pair<Long, Long> {
        val sortedTimestamp = listOfPoints.sortedBy { it.xValue }
        return Pair(sortedTimestamp.first().xValue, sortedTimestamp.last().xValue)
    }

    private fun getMinMaxValue(): Pair<Float, Float> {
        val sortedValue = listOfPoints.sortedBy { it.yValue }
        return Pair(sortedValue.first().yValue, sortedValue.last().yValue)
    }
}

@Immutable
data class LineChartData(
    val data: List<LineChartSeries>,
) :
    GridChartData {
    override val legendData: List<LegendItemData>
        get() = data.map {
            LegendItemData(
                name = it.dataName,
                symbolShape = SymbolShape.LINE,
                color = it.lineColor,
                dashed = it.dashedLine
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
        data.forEach {
            timeStamps.add(it.minTimestamp)
            timeStamps.add(it.maxTimeStamp)
            values.add(it.minValue)
            values.add(it.maxValue)
        }
        minX = timeStamps.minOrNull() ?: 0
        maxX = timeStamps.maxOrNull() ?: 0
        minY = values.minOrNull() ?: 0f
        maxY = values.maxOrNull() ?: 0f
    }
}
