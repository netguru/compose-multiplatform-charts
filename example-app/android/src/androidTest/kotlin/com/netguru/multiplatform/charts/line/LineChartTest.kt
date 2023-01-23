package com.netguru.multiplatform.charts.line

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karumi.shot.ScreenshotTest
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.Util.checkComposable
import com.netguru.multiplatform.charts.grid.YAxisTitleData
import com.netguru.multiplatform.charts.grid.axisscale.y.YAxisScaleDynamic
import com.netguru.multiplatform.charts.vertical
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LineChartTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun valuesAtLeast5_Y_roundedTo4_noTitle_X_noRounding_C_noLegend() {
        checkComposable(composeRule) {
            val data = Data.generateLineData(distanceToZero = 5f)
            LineChart(
                data = data,
                yAxisConfig = YAxisConfig(
                    markerLayout = { value ->
                        Text(text = value.toString())
                    },
                    yAxisTitleData = null,
                    scale = YAxisScaleDynamic(
                        chartData = data,
                        maxNumberOfHorizontalLines = Int.MAX_VALUE,
                        roundMarkersToMultiplicationOf = 4f,
                        forceShowingValueZeroLine = false,
                    ),
                ),
                xAxisConfig = XAxisConfig(
                    roundMarkersToMultiplicationOf = 1, // no rounding
                ),
                legendConfig = null,
                displayAnimation = ChartDisplayAnimation.Disabled,
            )
        }
    }

    @Test
    fun valuesAtLeast5ForceShowing0Line_Y_roundedTo4_titleOnTheLeft_X_noRounding_C_noLegend() {
        checkComposable(composeRule) {
            val data = Data.generateLineData(distanceToZero = 5f)
            LineChart(
                data = data,
                yAxisConfig = YAxisConfig(
                    markerLayout = { value ->
                        Text(text = value.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = "data title",
                                modifier = Modifier
                                    .vertical()
                            )
                        },
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = data,
                        maxNumberOfHorizontalLines = Int.MAX_VALUE,
                        roundMarkersToMultiplicationOf = 4f,
                        forceShowingValueZeroLine = true,
                    ),
                ),
                xAxisConfig = XAxisConfig(
                    roundMarkersToMultiplicationOf = 1, // no rounding
                ),
                legendConfig = null,
                displayAnimation = ChartDisplayAnimation.Disabled,
            )
        }
    }

    @Test
    fun valuesAboveAndBelowZero_Y_roundedToPoint4_titleOnTheRight_X_noRounding_customMarkers_alignToEdges_C_noLegend_dotsOnValues() {
        checkComposable(composeRule) {
            val data = Data.generateLineData(factor = 0.1f)
            LineChart(
                data = data,
                yAxisConfig = YAxisConfig(
                    markerLayout = { value ->
                        Text(text = value.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = "data title",
                                modifier = Modifier
                                    .vertical()
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Right,
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = data,
                        maxNumberOfHorizontalLines = Int.MAX_VALUE,
                        roundMarkersToMultiplicationOf = 0.4f,
                    ),
                ),
                xAxisConfig = XAxisConfig(
                    roundMarkersToMultiplicationOf = 1, // no rounding
                    markerLayout = {
                        val zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(it as Long), ZoneId.of("UTC"))
                        val str = zonedDateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
                        Text(text = str)
                    },
                    alignFirstAndLastToChartEdges = true,
                ),
                legendConfig = null,
                displayAnimation = ChartDisplayAnimation.Disabled,
                shouldDrawValueDots = true,
            )
        }
    }

    @Test
    fun valuesBelowMinus5_Y_roundedToPoint4_titleOnTheRight_X_noRounding_customMarkers_alignToEdges_C_noLegend_dotsOnValues() {
        checkComposable(composeRule) {
            val data = Data.generateLineData(distanceToZero = -5f)
            LineChart(
                data = data,
                yAxisConfig = YAxisConfig(
                    markerLayout = { value ->
                        Text(text = value.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = "data title",
                                modifier = Modifier
                                    .vertical()
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Right,
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = data,
                        maxNumberOfHorizontalLines = Int.MAX_VALUE,
                        roundMarkersToMultiplicationOf = 0.4f,
                        forceShowingValueZeroLine = false,
                    ),
                ),
                xAxisConfig = XAxisConfig(
                    roundMarkersToMultiplicationOf = 1, // no rounding
                    markerLayout = {
                        val zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(it as Long), ZoneId.of("UTC"))
                        val str = zonedDateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
                        Text(text = str)
                    },
                    alignFirstAndLastToChartEdges = true,
                ),
                legendConfig = null,
                displayAnimation = ChartDisplayAnimation.Disabled,
                shouldDrawValueDots = true,
            )
        }
    }

    @Test
    fun valuesAtLeast5_Y_justOneValue_noRounding_titleOnTheTop_X_noRounding_customMarkers_alignToEdges_C_defaultLegend() {
        checkComposable(composeRule) {
            val data = Data.generateLineData(numberOfLines = 1, numberOfPoints = 1, distanceToZero = 5f)
            LineChart(
                data = data,
                yAxisConfig = YAxisConfig(
                    markerLayout = { value ->
                        Text(text = value.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = "data title",
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Top,
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = data,
                        maxNumberOfHorizontalLines = Int.MAX_VALUE,
                        roundMarkersToMultiplicationOf = null,
                        forceShowingValueZeroLine = false,
                    ),
                ),
                xAxisConfig = XAxisConfig(
                    roundMarkersToMultiplicationOf = 1, // no rounding
                    markerLayout = {
                        val zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(it as Long), ZoneId.of("UTC"))
                        val str = zonedDateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
                        Text(text = str)
                    },
                    alignFirstAndLastToChartEdges = true,
                ),
                legendConfig = LegendConfig(),
                displayAnimation = ChartDisplayAnimation.Disabled,
                shouldDrawValueDots = false,
            )
        }
    }

    @Test
    fun valuesBelowMinus5ForceShowing0Line_Y_justOneValue_noRounding_titleOnTheTop_X_noRounding_customMarkers_alignToEdges_C_defaultLegend() {
        checkComposable(composeRule) {
            val data = Data.generateLineData(numberOfLines = 1, numberOfPoints = 1, distanceToZero = -5f)
            LineChart(
                data = data,
                yAxisConfig = YAxisConfig(
                    markerLayout = { value ->
                        Text(text = value.toString())
                    },
                    yAxisTitleData = YAxisTitleData(
                        labelLayout = {
                            Text(
                                text = "data title",
                            )
                        },
                        labelPosition = YAxisTitleData.LabelPosition.Top,
                    ),
                    scale = YAxisScaleDynamic(
                        chartData = data,
                        maxNumberOfHorizontalLines = Int.MAX_VALUE,
                        roundMarkersToMultiplicationOf = null,
                        forceShowingValueZeroLine = true,
                    ),
                ),
                xAxisConfig = XAxisConfig(
                    roundMarkersToMultiplicationOf = 1, // no rounding
                    markerLayout = {
                        val zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(it as Long), ZoneId.of("UTC"))
                        val str = zonedDateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
                        Text(text = str)
                    },
                    alignFirstAndLastToChartEdges = true,
                ),
                legendConfig = LegendConfig(),
                displayAnimation = ChartDisplayAnimation.Disabled,
                shouldDrawValueDots = false,
            )
        }
    }

    @Test
    fun intermittentValues_Y_noRounding_noTitle_X_roundToDays_customMarkers_hideOverlapping_alignToEdges_C_customLegend_doNotInterpolateOverNullValues() {
        checkComposable(composeRule) {
            val data = Data.generateIntermittentLineData()
            LineChart(
                data = data,
                yAxisConfig = YAxisConfig(
                    markerLayout = { value ->
                        Text(text = value.toString())
                    },
                    yAxisTitleData = null,
                    scale = YAxisScaleDynamic(
                        chartData = data,
                        roundMarkersToMultiplicationOf = null,
                        forceShowingValueZeroLine = false,
                    ),
                ),
                xAxisConfig = XAxisConfig(
                    roundMarkersToMultiplicationOf = 24 * 60 * 60 * 1000, // 1 day
                    markerLayout = {
                        val zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(it as Long), ZoneId.of("UTC"))
                        val str = zonedDateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
                        Text(text = str)
                    },
                    alignFirstAndLastToChartEdges = true,
                    hideMarkersWhenOverlapping = true,
                ),
                legendConfig = LegendConfig(
                    columnMinWidth = 50.dp,
                    legendItemLabel = { name, unit ->
                        Text(
                            text = "${name}_$unit",
                            fontSize = 10.sp,
                        )
                    }
                ),
                displayAnimation = ChartDisplayAnimation.Disabled,
                shouldDrawValueDots = true,
                shouldInterpolateOverNullValues = false,
            )
        }
    }

    @Test
    fun intermittentValues_Y_noRounding_noTitle_max2Line_X_roundToDays_customMarkers_hideOverlapping_alignToEdges_max3LinesC_customLegend() {
        checkComposable(composeRule) {
            val data = Data.generateIntermittentLineData()
            LineChart(
                data = data,
                yAxisConfig = YAxisConfig(
                    markerLayout = { value ->
                        Text(text = value.toString())
                    },
                    yAxisTitleData = null,
                    scale = YAxisScaleDynamic(
                        chartData = data,
                        roundMarkersToMultiplicationOf = null,
                        forceShowingValueZeroLine = false,
                        maxNumberOfHorizontalLines = 2,
                    ),
                ),
                xAxisConfig = XAxisConfig(
                    roundMarkersToMultiplicationOf = 24 * 60 * 60 * 1000, // 1 day
                    markerLayout = {
                        val zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(it as Long), ZoneId.of("UTC"))
                        val day = zonedDateTime.format(DateTimeFormatter.BASIC_ISO_DATE)
                        val hour = zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
                        Column {
                            Text(text = day)
                            Text(text = hour)
                        }
                    },
                    alignFirstAndLastToChartEdges = true,
                    hideMarkersWhenOverlapping = true,
                    maxVerticalLines = 3,
                ),
                legendConfig = LegendConfig(
                    columnMinWidth = 50.dp,
                    legendItemLabel = { name, unit ->
                        Text(
                            text = "${name}_$unit",
                            fontSize = 10.sp,
                        )
                    }
                ),
                displayAnimation = ChartDisplayAnimation.Disabled,
                shouldDrawValueDots = true,
            )
        }
    }
}
