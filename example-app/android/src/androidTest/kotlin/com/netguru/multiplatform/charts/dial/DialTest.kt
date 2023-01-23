package com.netguru.multiplatform.charts.dial

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
import com.netguru.multiplatform.charts.theme.ChartDefaults
import com.netguru.multiplatform.charts.Util.checkComposable
import org.junit.Rule
import org.junit.Test

class DialTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun range_0_100_value_50_UI_default() {
        checkComposable(composeRule) { Dial(value = 50f, minValue = 0f, maxValue = 100f,) }
    }

    @Test
    fun range_0_100_value_0_UI_default() {
        checkComposable(composeRule) { Dial(value = 0f, minValue = 0f, maxValue = 100f,) }
    }

    @Test
    fun range_0_100_value_100_UI_default() {
        checkComposable(composeRule) { Dial(value = 100f, minValue = 0f, maxValue = 100f,) }
    }

    @Test
    fun range_0_100_value_minus50_UI_default() {
        checkComposable(composeRule) { Dial(value = -50f, minValue = 0f, maxValue = 100f,) }
    }

    @Test
    fun range_0_100_value_150_UI_default() {
        checkComposable(composeRule) { Dial(value = 150f, minValue = 0f, maxValue = 100f,) }
    }

    @Test
    fun range_0_100_value_69_UI_custom_colors_and_labels() {
        checkComposable(composeRule) {
            Dial(
                value = 69f,
                minValue = 0f,
                maxValue = 100f,
                colors = DialChartDefaults.dialChartColors(
                    progressBarColor = DialProgressColors.SingleColor(Color.Blue),
                    gridScaleColor = Color.Magenta,
                ),
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
    fun range_0_100_value_69_UI_no_labels() {
        checkComposable(composeRule) {
            Dial(
                value = 69f,
                minValue = 0f,
                maxValue = 100f,
                minAndMaxValueLabel = { },
                mainLabel = { }
            )
        }
    }
}
