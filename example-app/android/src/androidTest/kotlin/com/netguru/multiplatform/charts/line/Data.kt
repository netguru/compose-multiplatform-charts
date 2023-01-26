package com.netguru.multiplatform.charts.line

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan
import kotlin.math.abs
import kotlin.math.sign

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

    /**
     * @param numberOfLines Number of lines to draw in the chart
     * @param numberOfPoints Number of points for each line
     * @param distanceToZero The actual distance might be a bit off due to sign of the largest number. Also, to have
     * the lines move down, you need to set this to a negative value.
     * @param factor The factor to use to multiply the values with. [distanceToZero] is not affected by it.
     */
    fun generateLineData(
        numberOfLines: Int = 4,
        numberOfPoints: Int = 5,
        distanceToZero: Float = 0f,
        factor: Float = 1f,
    ): LineChartData {

        val offset = ((numberOfLines + numberOfPoints) * factor + abs(distanceToZero)) * sign(distanceToZero)

        return LineChartData(
            series = (1..numberOfLines).map { line ->
                LineChartSeries(
                    dataName = "data $line",
                    lineColor = colors[line % colors.size],
                    listOfPoints = (1..numberOfPoints).map { point ->
                        val sign = if ((point + line) % 2 == 0) -1 else 1
                        val value = ((line + point) * sign * factor) + offset
                        LineChartPoint(
                            x = DateTime.fromString("2021-12-31")
                                .plus(getTimesSpanForPoint(point))
                                .utc
                                .unixMillisLong,
                            y = value,
                        )
                    }
                )
            },
            dataUnit = "unit",
        )
    }

    fun generateIntermittentLineData(): LineChartData {
        return LineChartData(
            series = listOf(
                LineChartSeries(
                    dataName = "data left",
                    lineColor = colors[0],
                    listOfPoints = (1..7).map { point ->
                        LineChartPoint(
                            x = DateTime.fromString("2021-12-31")
                                .plus(getTimesSpanForPoint(point))
                                .utc
                                .unixMillisLong,
                            y = if (point == 3 || point == 5) null else point.toFloat()-3.6f
                        )
                    },
                ),
                LineChartSeries(
                    dataName = "lots of data 2",
                    lineColor = colors[1],
                    listOfPoints = (1..7).map { point ->
                        LineChartPoint(
                            x = DateTime.fromString("2021-12-31")
                                .plus(getTimesSpanForPoint(point))
                                .utc
                                .unixMillisLong,
                            y = if (point == 1 || point > 5) null else (6 - point).toFloat()-3.6f
                        )
                    },
                ),
            ),
            dataUnit = "leftUnit",
        )
    }

    private fun getTimesSpanForPoint(point: Int): TimeSpan = TimeSpan(point * 24 * 60 * 60 * 1000.0)
}
