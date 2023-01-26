package com.netguru.multiplatform.charts.application.screen

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.netguru.multiplatform.charts.grid.axisscale.y.YAxisScaleDynamic
import com.netguru.multiplatform.charts.line.LineChartData
import com.netguru.multiplatform.charts.line.LineChartPoint
import com.netguru.multiplatform.charts.line.LineChartSeries
import com.netguru.multiplatform.charts.line.LineChartWithTwoYAxisSets
import com.netguru.multiplatform.charts.line.TooltipConfig
import com.netguru.multiplatform.charts.line.XAxisConfig
import com.netguru.multiplatform.charts.line.YAxisConfig
import com.netguru.multiplatform.charts.vertical
import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan

@Composable
fun LineChartWithTwoYAxisScreen() {

    val colorLeft = Color(0xFFFFCC00)
    val colorRight = Color(0xFF9D78E6)

    val now = DateTime.now()
    val lineDataLeft = remember {
        LineChartData(
            series = listOf(
                LineChartSeries(
                    dataName = "data left",
                    lineColor = colorLeft,
                    listOfPoints = (1..30).map { point ->
                        LineChartPoint(
                            x = now.minus(TimeSpan(point * 24 * 60 * 60 * 1000.0)).unixMillisLong,
                            y = if (point in 15..20 && point != 17) null else point.toFloat()
                        )
                    },
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 10f), 0f),
                ),
//                LineChartSeries(
//                    dataName = "lots of data 2",
//                    lineColor = colorLeft,
//                    listOfPoints = (1..(24 * 14)).filter { it % 8 == 0 }.map { point ->
//                        val sine = sin(point.toFloat() * PI / 180).toFloat()
//                        LineChartPoint(
//                            x = now.minus(TimeSpan(point * 5 * 60 * 1000.0)).unixMillisLong,
//                            y = sine,
//                        )
//                    }
//                ),
            ),
            dataUnit = "leftUnit",
        )
    }

    val lineDataRight = remember {
        LineChartData(
            series = listOf(
                LineChartSeries(
                    dataName = "data right",
                    dataNameShort = "short name",
                    lineColor = colorRight,
                    listOfPoints = (1..15).map { point ->
                        LineChartPoint(
                            x = now.minus(TimeSpan(point * 24 * 60 * 60 * 1000.0)).unixMillisLong,
                            y = if (point == 3 || point == 5) null else (10 - (point * point)).toFloat()
                        )
                    }
                ),
//                LineChartSeries(
//                    dataName = "lots of data",
//                    lineColor = colorRight,
//                    listOfPoints = (1..(24 * 14)).filter { it % 8 == 0 }.map { point ->
//                        val sine = sin(point.toFloat() * PI / 180).toFloat()
//                        LineChartPoint(
//                            x = now.minus(TimeSpan(point * 5 * 60 * 1000.0)).unixMillisLong,
//                            y = sine + 1.05f,
//                        )
//                    }
//                ),
            ),
            dataUnit = null,
        )
    }

    val yAxisLabelLeft: @Composable (value: Any) -> Unit = { value ->
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 12.sp,
            text = value.toString(),
            textAlign = TextAlign.End,
            color = colorLeft,
        )
    }

    val yAxisLabelRight: @Composable (value: Any) -> Unit = { value ->
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 12.sp,
            text = value.toString(),
            textAlign = TextAlign.Start,
            color = colorRight,
        )
    }

    ScrollableScreen {
        SpacedColumn {

            TitleText(text = "Line chart with two Y axis")
            LineChartWithTwoYAxisSets(
                leftYAxisData = lineDataLeft,
                leftYAxisConfig = YAxisConfig(
                    markerLayout = {
                        yAxisLabelLeft(it.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = lineDataLeft.series.first().dataName,
                                color = colorLeft,
                                modifier = Modifier
                                    .vertical()
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Left,
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = lineDataLeft,
                        roundMarkersToMultiplicationOf = null,
                        forceShowingValueZeroLine = false,
                    ),
                ),
                rightYAxisData = null,//lineDataRight,
                rightYAxisConfig = YAxisConfig(
                    markerLayout = {
                        yAxisLabelRight(it.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = lineDataRight.series.first().dataName,
                                color = colorRight,
                                modifier = Modifier
                                    .vertical()
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Right,
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = lineDataRight,
                        roundMarkersToMultiplicationOf = 0.1f,
                        forceShowingValueZeroLine = false,
                    ),
                ),
                modifier = Modifier
                    .height(300.dp),
                xAxisConfig = XAxisConfig(
                    markerLayout = {
                        Text(
                            fontSize = 12.sp,
                            text = DateTime.fromUnix(it as Long).format("HH:mm"),
                            textAlign = TextAlign.Center
                        )
                    }
                ),
                tooltipConfig = TooltipConfig(
                    headerLabel = { it, _ ->
                        Text(
                            text = DateTime.fromUnix(it as Long).format("HH:mm"),
                            style = MaterialTheme.typography.overline
                        )
                    },
                    width = null,
                    showInterpolatedValues = false,
                ),
                displayAnimation = ChartDisplayAnimation.Sequenced(),
                shouldDrawValueDots = true,
                legendConfig = null,
                shouldInterpolateOverNullValues = false,
            )

            HorizontalDivider()


            TitleText(text = "Line chart with two Y axis")
            LineChartWithTwoYAxisSets(
                leftYAxisData = lineDataLeft,
                leftYAxisConfig = YAxisConfig(
                    markerLayout = {
                        yAxisLabelLeft(it.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = lineDataLeft.series.first().dataName,
                                color = colorLeft,
//                                modifier = Modifier
//                                    .vertical()
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Top,
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = lineDataLeft,
                        roundMarkersToMultiplicationOf = 1f,
                    ),
                ),
                rightYAxisData = lineDataRight,
                rightYAxisConfig = YAxisConfig(
                    markerLayout = {
                        yAxisLabelRight(it.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = lineDataRight.series.first().dataName,
                                color = colorRight,
//                                modifier = Modifier
//                                    .vertical()
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Top,
//                        labelPosition = YAxisTitleData.LabelPosition.Right,
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = lineDataRight,
                        roundMarkersToMultiplicationOf = 0.1f,
                    ),
                ),
                modifier = Modifier
                    .height(300.dp),
                xAxisConfig = XAxisConfig(
                    markerLayout = {
                        Text(
                            fontSize = 12.sp,
                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                            textAlign = TextAlign.Center
                        )
                    }
                ),
                tooltipConfig = TooltipConfig(
                    headerLabel = { it, _ ->
                        Text(
                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                            style = MaterialTheme.typography.overline
                        )
                    },
                    showInterpolatedValues = false,
                    showEnlargedPointOnLine = true,
                ),
                displayAnimation = ChartDisplayAnimation.Sequenced(),
                shouldDrawValueDots = true,
                legendConfig = null,
                shouldInterpolateOverNullValues = false,
            )

            HorizontalDivider()

            TitleText(text = "Line chart with two Y axis with legend")
            LineChartWithTwoYAxisSets(
                modifier = Modifier
                    .height(300.dp),
                leftYAxisData = lineDataLeft,
                leftYAxisConfig = YAxisConfig(
                    markerLayout = {
                        yAxisLabelLeft(it.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = lineDataLeft.series.first().dataName,
                                color = colorLeft,
                                modifier = Modifier
                                    .vertical()
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Right,
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = lineDataLeft,
                        roundMarkersToMultiplicationOf = 1f,
                    ),
                ),
                rightYAxisData = lineDataRight,
                rightYAxisConfig = YAxisConfig(
                    markerLayout = {
                        yAxisLabelRight(it.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = lineDataRight.series.first().dataName,
                                color = colorRight,
                                modifier = Modifier
                                    .vertical()
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Right,
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = lineDataRight,
                        roundMarkersToMultiplicationOf = 1f,
                    ),
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
                shouldDrawValueDots = true,
            )
        }
    }
}
