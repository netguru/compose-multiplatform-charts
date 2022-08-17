package com.netguru.multiplatform.charts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.karumi.shot.ScreenshotTest
import com.netguru.multiplatform.charts.theme.ChartDefaults
import com.netguru.multiplatform.charts.theme.LocalChartColors

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
