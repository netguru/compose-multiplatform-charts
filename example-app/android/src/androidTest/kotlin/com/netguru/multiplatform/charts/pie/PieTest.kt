package com.netguru.multiplatform.charts.pie

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.karumi.shot.ScreenshotTest
import com.netguru.multiplatform.charts.Util.checkComposable
import org.junit.Rule
import org.junit.Test

class PieTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun twoCategories() {
        checkComposable(composeRule) {
            PieChart(
                data = listOf(
                    PieChartData("first", 22.0, Color.Blue),
                    PieChartData("second", 22.0, Color.Yellow)
                ),
                config = PieChartConfig(
                    thickness = 80.dp
                )
            )
        }
    }

    @Test
    fun threeCategories() {
        checkComposable(composeRule) {
            PieChart(
                data = listOf(
                    PieChartData("first", 22.0, Color.Blue),
                    PieChartData("second", 22.0, Color.Yellow),
                    PieChartData("third", 22.0, Color.Green)
                ),
                config = PieChartConfig(
                    thickness = 80.dp
                )
            )
        }
    }

    @Test
    fun twoCategories_withZeroValue() {
        checkComposable(composeRule) {
            PieChart(
                data = listOf(
                    PieChartData("first", 22.0, Color.Blue),
                    PieChartData("second", 0.0, Color.Yellow)
                ),
                config = PieChartConfig(
                    thickness = 80.dp
                )
            )
        }
    }
}
