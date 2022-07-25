package com.netguru.charts.dial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karumi.shot.ScreenshotTest
import com.netguru.charts.theme.ChartDefaults
import com.netguru.charts.Util.checkComposable
import org.junit.Rule
import org.junit.Test

class PercentageDialTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun value_50_UI_default() {
        checkComposable(composeRule) { PercentageDial(percentage = 50) }
    }

    @Test
    fun value_0_UI_default() {
        checkComposable(composeRule) { PercentageDial(percentage = 0) }
    }

    @Test
    fun value_100_UI_default() {
        checkComposable(composeRule) { PercentageDial(percentage = 100) }
    }

    @Test
    fun value_minus100_UI_default() {
        checkComposable(composeRule) { PercentageDial(percentage = -100) }
    }

    @Test
    fun value_150_UI_default() {
        checkComposable(composeRule) { PercentageDial(percentage = 150) }
    }

    @Test
    fun value_69_UI_custom_colors_and_labels() {
        checkComposable(composeRule) {
            PercentageDial(
                percentage = 69,
                colors = ChartDefaults.chartColors(
                    primary = Color.Blue,
                    grid = Color.Magenta,
                ).dialColors,
                minAndMaxValueLabel = {
                    Text(
                        text = it.toString(),
                        color = Color.Blue,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                },
                mainLabel = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = it.toString(), color = Color.Blue, fontSize = 40.sp)
                        Text(text = "tests", color = Color.Magenta, fontSize = 24.sp)
                    }
                }
            )
        }
    }

    @Test
    fun value_69_UI_no_labels() {
        checkComposable(composeRule) {
            PercentageDial(
                percentage = 69,
                minAndMaxValueLabel = { },
                mainLabel = { }
            )
        }
    }
}
