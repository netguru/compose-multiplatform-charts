package com.netguru.charts.gasbottle

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import com.karumi.shot.ScreenshotTest
import com.netguru.charts.Util.checkComposable
import com.netguru.charts.theme.ChartDefaults
import org.junit.Rule
import org.junit.Test

class GasBottleTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun value0_defaultUI() {
        checkComposable(composeRule) {
            GasBottle(percentage = 0f)
        }
    }

    @Test
    fun value50_defaultUI() {
        checkComposable(composeRule) {
            GasBottle(percentage = 50f)
        }
    }

    @Test
    fun value100_defaultUI() {
        checkComposable(composeRule) {
            GasBottle(percentage = 100f)
        }
    }

    @Test
    fun value25_customUI() {
        checkComposable(composeRule) {
            GasBottle(
                percentage = 25f,
                chartColors = ChartDefaults.chartColors(
                    emptyGasBottle = Color.Yellow,
                    fullGasBottle = Color.Blue,
                )
            )
        }
    }

    @Test
    fun value100_customUI() {
        checkComposable(composeRule) {
            GasBottle(
                percentage = 100f,
                chartColors = ChartDefaults.chartColors(
                    emptyGasBottle = Color.Yellow,
                    fullGasBottle = Color.Blue,
                )
            )
        }
    }
}