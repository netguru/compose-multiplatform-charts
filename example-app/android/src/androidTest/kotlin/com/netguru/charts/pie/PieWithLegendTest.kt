package com.netguru.charts.pie

import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
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
                columns = 2,
                legendItemLabel = {
                    Text(text = "${it.name} (${it.value})")
                }
            )
        }
    }
}