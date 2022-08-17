package com.netguru.multiplatform.charts.line

import androidx.compose.ui.graphics.Color
import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan

internal object Data {

    private val colors = listOf(
        Color.Blue,
        Color.Yellow,
        Color.Cyan,
        Color.Black,
        Color.DarkGray,
        Color.Green,
        Color.LightGray,
        Color.Magenta,
        Color.Red,
    )

    fun generateLineData(nOfLines: Int) : LineChartData {
        return LineChartData(
            series = (1..nOfLines).map {
                LineChartSeries(
                    dataName = "data $it",
                    lineColor = colors[it % colors.size],
                    listOfPoints = (1..5).map { point ->
                        val sign = if((point+it) % 2 == 0) -1 else 1
                        val value = (it + point) * sign
                        LineChartPoint(
                            x = DateTime.fromString("2021-12-31")
                                .minus(TimeSpan(point * 24 * 60 * 60 * 1000.0))
                                .utc
                                .unixMillisLong,
                            y = value.toFloat(),
                        )
                    }
                )
            },
        )
    }
}
