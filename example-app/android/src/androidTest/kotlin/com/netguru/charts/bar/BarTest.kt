package com.netguru.charts.bar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.karumi.shot.ScreenshotTest
import com.netguru.charts.Util.checkComposable
import com.netguru.charts.theme.ChartDefaults
import org.junit.Rule
import org.junit.Test

class BarTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    private fun testBasicChart(nOfCategories: Int, nOfEntries: Int, valueTypes: Data.ValueTypes) {
        val data = Data.generateData(nOfCategories, nOfEntries, valueTypes)
        checkComposable(composeRule) {
            BarChart(
                data = data,
                config = BarChartConfig(
                    thickness = 8.dp,
                    cornerRadius = 0.dp,
                    barsSpacing = 1.dp
                )
            )
        }
    }

    @Suppress("SameParameterValue")
    private fun testCustomChart(nOfCategories: Int, nOfEntries: Int, valueTypes: Data.ValueTypes) {
        val data = Data.generateData(nOfCategories, nOfEntries, valueTypes)
        checkComposable(composeRule) {
            BarChart(
                data = data,
                config = BarChartConfig(
                    thickness = 8.dp,
                    cornerRadius = 0.dp,
                    barsSpacing = 1.dp
                ),
                colors = ChartDefaults.chartColors(grid = Color.Red).barChartColors,
                xAxisLabel = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = it.toString())
                        Text(text = "testing")
                    }
                },
                yAxisLabel = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = it.toString())
                        Text(text = "units")
                    }
                },
                maxHorizontalLinesCount = 2,
            )
        }
    }

    /********************************
     * zero values
     ********************************/

//    @Test
//    fun oneCategory_oneBar_zeroValues() { TODO BN-3899
//        val data = Data.generateData(1, 1, Data.ValueTypes.ZERO)
//        checkComposable(composeRule) {
//            BarChart(data = data)
//        }
//    }

    /********************************
     * positive values
     ********************************/

    @Test
    fun oneCategory_oneBar_positiveValues() {
        testBasicChart(1, 1, Data.ValueTypes.POSITIVE)
    }

    @Test
    fun oneCategory_twoBars_positiveValues() {
        testBasicChart(1, 2, Data.ValueTypes.POSITIVE)
    }

    @Test
    fun oneCategory_fiveBars_positiveValues() {
        testBasicChart(1, 5, Data.ValueTypes.POSITIVE)
    }

    @Test
    fun twoCategories_twoBars_positiveValues() {
        testBasicChart(2, 2, Data.ValueTypes.POSITIVE)
    }

    @Test
    fun threeCategories_oneBar_positiveValues() {
        testBasicChart(3, 1, Data.ValueTypes.POSITIVE)
    }

    @Test
    fun oneCategory_oneBar_positiveValues_customUI() {
        testCustomChart(1, 1, Data.ValueTypes.POSITIVE)
    }

    /********************************
     * negative values
     ********************************/

    @Test
    fun oneCategory_oneBar_negativeValues() {
        testBasicChart(1, 1, Data.ValueTypes.NEGATIVE)
    }

    @Test
    fun oneCategory_twoBars_negativeValues() {
        testBasicChart(1, 2, Data.ValueTypes.NEGATIVE)
    }

    @Test
    fun twoCategories_twoBars_negativeValues() {
        testBasicChart(2, 2, Data.ValueTypes.NEGATIVE)
    }

    @Test
    fun oneCategory_oneBar_negativeValues_customUI() {
        testCustomChart(1, 1, Data.ValueTypes.NEGATIVE)
    }

    /********************************
     * positive and negative values
     ********************************/

    @Test
    fun twoCategories_twoBars_positiveAndNegativeValues() {
        testBasicChart(2, 2, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
    }

    @Test
    fun oneCategory_twoBars_positiveAndNegativeValues_customUI() {
        testCustomChart(1, 2, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
    }
}
