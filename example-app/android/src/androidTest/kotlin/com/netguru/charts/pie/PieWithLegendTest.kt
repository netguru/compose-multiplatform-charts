package com.netguru.charts.pie

import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.karumi.shot.ScreenshotTest
import com.netguru.charts.Util.checkComposable
import org.junit.Rule
import org.junit.Test

class PieWithLegendTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun twoCategories() {
        checkComposable(composeRule) {
            PieChartWithLegend(
                config = PieChartConfig(thickness = 80.dp, legendIcon = LegendIcon.ROUND),
                pieChartData = listOf(
                    PieChartData("first", 22.0, Color.Blue),
                    PieChartData("second", 22.0, Color.Yellow)
                )
            )
        }
    }

    @Test
    fun threeCategories() {
        checkComposable(composeRule) {
            PieChartWithLegend(
                config = PieChartConfig(thickness = 80.dp, legendIcon = LegendIcon.ROUND),
                pieChartData = listOf(
                    PieChartData("first", 22.0, Color.Blue),
                    PieChartData("second", 22.0, Color.Yellow),
                    PieChartData("third", 22.0, Color.Green),
                )
            )
        }
    }

    @Test
    fun twoCategories_withZeroValue() {
        checkComposable(composeRule) {
            PieChartWithLegend(
                config = PieChartConfig(thickness = 80.dp, legendIcon = LegendIcon.ROUND),
                pieChartData = listOf(
                    PieChartData("first", 22.0, Color.Blue),
                    PieChartData("second", 0.0, Color.Yellow),
                )
            )
        }
    }

    @Test
    fun threeCategories_customUI() {
        checkComposable(composeRule) {
            PieChartWithLegend(
                pieChartData = listOf(
                    PieChartData("first", 22.0, Color.Blue),
                    PieChartData("second", 22.0, Color.Yellow),
                    PieChartData("third", 22.0, Color.Green),
                ),
                config = PieChartConfig(
                    thickness = 80.dp,
                    numberOfColsInLegend = 2,
                    legendIcon = LegendIcon.ROUND
                ),
                legendItemLabel = {
                    Text(text = "${it.name} (${it.value})")
                }
            )
        }
    }
}
