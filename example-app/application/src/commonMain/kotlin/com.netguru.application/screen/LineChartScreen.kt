package com.netguru.application.screen

import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.netguru.application.ScrollableScreen
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.ChartAnimation
import com.netguru.charts.line.LineChart
import com.netguru.charts.line.LineChartData
import com.netguru.charts.line.LineChartPoint
import com.netguru.charts.line.LineChartSeries
import com.netguru.charts.line.LineChartWithLegend
import com.netguru.common.HorizontalDivider
import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan

@Composable
fun LineChartScreen() {

    val lineData = remember {
        LineChartData(
            series = (1..3).map {
                LineChartSeries(
                    dataName = "data $it",
                    lineColor = listOf(
                        Color(0xFFFFCC00),
                        Color(0xFF00D563),
                        Color(0xFF32ADE6),
                    )[it - 1],
                    listOfPoints = (1..10).map { point ->
                        LineChartPoint(
                            x = DateTime.now().minus(TimeSpan(point * 24 * 60 * 60 * 1000.0)).unixMillisLong,
                            y = (1..15).random().toFloat(),
                        )
                    }
                )
            },
        )
    }

    ScrollableScreen {
        SpacedColumn {

            TitleText(text = "Line chart")
            LineChart(
                lineChartData = lineData,
                modifier = Modifier
                    .height(300.dp),
                xAxisLabel = {
                    Text(
                        fontSize = 12.sp,
                        text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                        textAlign = TextAlign.Center
                    )
                },
                overlayHeaderLabel = {
                    Text(
                        text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                        style = MaterialTheme.typography.overline
                    )
                },
                animation = ChartAnimation.Sequenced()
            )

            HorizontalDivider()

            TitleText(text = "Line chart with legend")
            LineChartWithLegend(
                modifier = Modifier
                    .height(300.dp),
                lineChartData = lineData,
                maxVerticalLines = 5,
                xAxisLabel = {
                    Text(
                        fontSize = 12.sp,
                        text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                        textAlign = TextAlign.Center
                    )
                },
                overlayHeaderLabel = {
                    Text(
                        text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                        style = MaterialTheme.typography.overline
                    )
                },
                animation = ChartAnimation.Sequenced()
            )
        }
    }
}
