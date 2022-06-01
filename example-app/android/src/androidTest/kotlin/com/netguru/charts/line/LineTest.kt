package com.netguru.charts.line

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karumi.shot.ScreenshotTest
import com.netguru.charts.Util.checkComposable
import com.netguru.charts.theme.ChartDefaults
import com.soywiz.klock.DateTime
import org.junit.Rule
import org.junit.Test

class LineTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun mixedValues_defaultUI() {
        checkComposable(composeRule) {
            LineChart(
                lineChartData = Data.generateLineData(3)
            )
        }
    }

    @Test
    fun mixedValues_customUI() {
        val data = Data.generateLineData(3)
        checkComposable(composeRule) {
            LineChart(
                lineChartData = data
                    .copy(
                        series = data.series.map {
                            it.copy(
                                lineWidth = 12.dp,
                                fillColor = Color.Yellow,
                                lineColor = Color.Blue,
                                dashedLine = true,
                            )
                        }
                    ),
                chartColors = ChartDefaults.chartColors(
                    grid = Color.Magenta,
                    surface = Color.Cyan,
                    overlayLine = Color.Gray,
                ),
                xAxisLabel = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            fontSize = 12.sp,
                            text = DateTime.fromUnix(it as Long).format("yyyy-MM-dd"),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "midday"
                        )
                    }
                },
                yAxisLabel = {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = it.toString())
                        Text(text = "units")
                    }
                },
                maxVerticalLines = 2,
                maxHorizontalLines = 2,
            )
        }
    }
}