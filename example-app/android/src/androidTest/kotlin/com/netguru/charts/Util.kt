package com.netguru.charts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.karumi.shot.ScreenshotTest
import com.netguru.charts.theme.ChartDefaults
import com.netguru.charts.theme.LocalChartColors

object Util {
    fun ScreenshotTest.checkComposable(composeRule: ComposeContentTestRule, contentToCheck: @Composable () -> Unit) {
        composeRule.setContent {
            CompositionLocalProvider(LocalChartColors provides ChartDefaults.chartColors()) {
                contentToCheck()
            }
        }

        compareScreenshot(composeRule)
    }
}
