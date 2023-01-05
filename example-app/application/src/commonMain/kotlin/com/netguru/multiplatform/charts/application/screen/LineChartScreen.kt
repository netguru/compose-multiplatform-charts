package com.netguru.multiplatform.charts.application.screen

import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.application.ScrollableScreen
import com.netguru.multiplatform.charts.application.SpacedColumn
import com.netguru.multiplatform.charts.application.TitleText
import com.netguru.multiplatform.charts.common.HorizontalDivider
import com.netguru.multiplatform.charts.grid.YAxisTitleData
import com.netguru.multiplatform.charts.line.LineChart
import com.netguru.multiplatform.charts.line.LineChartData
import com.netguru.multiplatform.charts.line.LineChartPoint
import com.netguru.multiplatform.charts.line.LineChartSeries
import com.netguru.multiplatform.charts.line.TooltipConfig
import com.netguru.multiplatform.charts.line.XAxisConfig
import com.netguru.multiplatform.charts.line.YAxisConfig
import com.netguru.multiplatform.charts.vertical
import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun LineChartScreen() {

    val lineData = remember {
        LineChartData(
            series = (1..3).map { seriesNumber ->
                LineChartSeries(
                    dataName = "data $seriesNumber",
                    lineColor = listOf(
                        Color(0xFFFFCC00),
                        Color(0xFF00D563),
                        Color(0xFF32ADE6),
                    )[seriesNumber - 1],
                    listOfPoints = (1..20).map { point ->
                        LineChartPoint(
                            x = DateTime.now().minus(TimeSpan(point * 24 * 60 * 60 * 1000.0)).unixMillisLong,
                            y = (1..15).random().toFloat(),
                        )
                    },
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f).takeIf { seriesNumber == 2 }
                )
            },
            dataUnit = "unit",
        )
    }

    val lineDataWithLotsOfPoints = remember {
        val now = DateTime.now()
        LineChartData(
            series = listOf(
                LineChartSeries(
                    dataName = "lots of data",
                    lineColor = Color(0xFFFFCC00),
                    listOfPoints = (1..(24 * 14)).filter { it % 8 == 0 }.map { point ->
                        val sine = sin(point.toFloat() * PI / 180).toFloat()
                        LineChartPoint(
                            x = now.minus(TimeSpan(point * 5 * 60 * 1000.0)).unixMillisLong,
                            y = sine + 1.05f,
                        )
                    },
                ),
                LineChartSeries(
                    dataName = "lots of data 2",
                    lineColor = Color(0xFF32ADE6),
                    listOfPoints = (1..(24 * 14)).filter { it % 8 == 0 }.map { point ->
                        val sine = sin(point.toFloat() * PI / 180).toFloat()
                        LineChartPoint(
                            x = now.minus(TimeSpan(point * 5 * 60 * 1000.0)).unixMillisLong,
                            y = sine,
                        )
                    }
                ),
            ),
            dataUnit = null,
        )
    }


    ScrollableScreen {
        SpacedColumn {

            TitleText(text = "Line chart")
            LineChart(
                modifier = Modifier
                    .height(300.dp),
                data = lineData,
//                maxVerticalLines = 25,
//                xAxisOptions = XAxisOptions(
//                    markerLayout = {
//                        Text(
//                            fontSize = 12.sp,
//                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
//                            textAlign = TextAlign.Center,
//                        )
//                    }
//                ),
//                overlayOptions = OverlayOptions(
//                    headerLabel = { it, _ ->
//                        Text(
//                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
//                            style = MaterialTheme.typography.overline
//                        )
//                    }
//                ),
//                animation = ChartAnimation.Sequenced(),
//                drawPoints = true,
//                legendOptions = null,
            )

            HorizontalDivider()

            TitleText(text = "Line chart with legend")
            LineChart(
                modifier = Modifier
                    .height(300.dp),
                data = lineData,
                xAxisConfig = XAxisConfig(
                    markerLayout = {
                        Text(
                            fontSize = 12.sp,
                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                            textAlign = TextAlign.Center
                        )
                    },
                    maxVerticalLines = 5,
                ),
                tooltipConfig = TooltipConfig(
                    headerLabel = { it, _ ->
                        Text(
                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                            style = MaterialTheme.typography.overline
                        )
                    }
                ),
                displayAnimation = ChartDisplayAnimation.Sequenced()
            )

            HorizontalDivider()

            TitleText(text = "Line chart with only one data point")
            LineChart(
                modifier = Modifier
                    .height(300.dp),
                data = LineChartData(
                    series = listOf(
                        LineChartSeries(
                            dataName = "data 1",
                            lineColor = Color(0xFFFFCC00),
                            listOfPoints = listOf(
                                LineChartPoint(
                                    x = DateTime.now().unixMillisLong,
                                    y = 18f,
                                ),
                            )
                        ),
                    ),
                    dataUnit = "unit",
                ),
                yAxisConfig = YAxisConfig(
                    roundMinMaxClosestTo = 1f
                ),
                xAxisConfig = XAxisConfig(
                    markerLayout = {
                        Text(
                            fontSize = 12.sp,
                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                            textAlign = TextAlign.Center
                        )
                    },
                    maxVerticalLines = 5,
                ),
                tooltipConfig = TooltipConfig(
                    headerLabel = { it, _ ->
                        Text(
                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                            style = MaterialTheme.typography.overline
                        )
                    }
                ),
                displayAnimation = ChartDisplayAnimation.Sequenced(),
                legendConfig = null,
            )

            TitleText(text = "Line chart with only two data points, both with the same value, and null between them")
            LineChart(
                modifier = Modifier
                    .height(300.dp),
                data = LineChartData(
                    series = listOf(
                        LineChartSeries(
                            dataName = "data 1",
                            lineColor = Color(0xFFFFCC00),
                            listOfPoints = listOf(
//                                    LineChartPoint(
//                                        x = DateTime.now().unixMillisLong,
//                                        y = 18f,
//                                    ),
//                                    LineChartPoint(
//                                        x = DateTime.now().unixMillisLong + 1 * 24 * 60 * 60 * 1000L,
//                                        y = null,
//                                    ),
//                                    LineChartPoint(
//                                        x = DateTime.now().unixMillisLong + 2 * 24 * 60 * 60 * 1000L,
//                                        y = 18f,
//                                    ),

                                LineChartPoint(x = 1660600800000, y = 36.0f),
                                LineChartPoint(x = 1660687200000, y = null),
                                LineChartPoint(x = 1660773600000, y = 76.5f),
                                LineChartPoint(x = 1660860000000, y = 83.7f),
                                LineChartPoint(x = 1660946400000, y = null),
                                LineChartPoint(x = 1661032800000, y = null),
                                LineChartPoint(x = 1661119200000, y = 216.0f)
                            )
                        ),
                    ),
                    dataUnit = "unit",
                ),
                yAxisConfig = YAxisConfig(
                    roundMinMaxClosestTo = 1f,
                ),
                xAxisConfig = XAxisConfig(
                    markerLayout = {
                        Text(
                            fontSize = 12.sp,
                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                            textAlign = TextAlign.Center
                        )
                    },
                    maxVerticalLines = 10,
                ),
                tooltipConfig = TooltipConfig(
                    headerLabel = { it, _ ->
                        Text(
                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                            style = MaterialTheme.typography.overline
                        )
                    }
                ),
                displayAnimation = ChartDisplayAnimation.Sequenced(),
                legendConfig = null,
                shouldDrawValueDots = true,
            )

            TitleText(text = "Line chart with legend and with lots of data")
            LineChart(
                modifier = Modifier
                    .height(300.dp),
                data = lineDataWithLotsOfPoints,
                yAxisConfig = YAxisConfig(
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = lineDataWithLotsOfPoints.series.first().dataName,
                                color = lineDataWithLotsOfPoints.series.first().lineColor,
                                modifier = Modifier
                                    .vertical()
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Right,
                    ),
                    roundMinMaxClosestTo = 0.1f,
                ),
                xAxisConfig = XAxisConfig(
                    markerLayout = {
                        Text(
                            fontSize = 12.sp,
                            text = DateTime.fromUnix(it as Long).format("HH:mm"),
                            textAlign = TextAlign.Center
                        )
                    },
                    maxVerticalLines = 5,
                ),
                tooltipConfig = TooltipConfig(
                    headerLabel = { it, _ ->
                        Text(
                            text = DateTime.fromUnix(it as Long).format("HH:mm"),
                            style = MaterialTheme.typography.overline
                        )
                    }
                ),
                displayAnimation = ChartDisplayAnimation.Sequenced(),
                shouldDrawValueDots = true,
            )
        }
    }
}
