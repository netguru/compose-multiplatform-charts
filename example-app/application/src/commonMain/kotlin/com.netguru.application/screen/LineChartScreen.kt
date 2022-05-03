package com.netguru.application.screen

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.line.LineChart
import com.netguru.charts.line.LineChartData
import com.netguru.charts.line.LineChartPoint
import com.netguru.charts.line.LineChartSeries
import com.netguru.charts.line.LineChartWithLegend
import com.netguru.common.AppChartColors
import com.netguru.common.HorizontalDivider
import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan

@Composable
fun LineChartScreen(){

    val lineData = LineChartData(
        series = (1..3).map {
            LineChartSeries(
                dataName = "data $it",
                lineColor = listOf(
                    Color.Red,
                    Color.Green,
                    Color.Blue,
                )[it - 1],
                listOfPoints = (1..10).map { point ->
                    LineChartPoint(
                        timestamp = DateTime.now().minus(TimeSpan(point * 24 * 60 * 60 * 1000.0)).unixMillisLong,
                        value = (1 .. 15).random().toFloat(),
                    )
                }
            )
        }
    )

    SpacedColumn {

        TitleText(text = "Line chart")
        LineChart(
            lineChartData = lineData,
            xAxisValueFormatter = { DateTime.fromUnix(it).format("yyyy-MM-dd") },
            timeFormatter = { DateTime.fromUnix(it).format("yyyy-MM-dd") },
            chartColors = AppChartColors(),
            modifier = Modifier
                .height(300.dp)
        )

        HorizontalDivider()

        TitleText(text = "Line chart with legend")
        LineChartWithLegend(
            lineChartData = lineData,
            xAxisValueFormatter = { DateTime.fromUnix(it).format("yyyy-MM-dd") },
            timeFormatter = { DateTime.fromUnix(it).format("yyyy-MM-dd") },
            maxVerticalLines = 5,
            animate = true,
            chartColors = AppChartColors(),
            modifier = Modifier
                .height(300.dp),
        )
    }
}