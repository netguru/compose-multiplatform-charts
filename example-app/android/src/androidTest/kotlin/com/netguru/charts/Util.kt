package com.netguru.charts

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import com.karumi.shot.ScreenshotTest

object Util {
    fun ScreenshotTest.checkComposable(composeRule: ComposeContentTestRule, contentToCheck: @Composable () -> Unit) {
        composeRule.setContent { contentToCheck() }

        compareScreenshot(composeRule)
    }
}