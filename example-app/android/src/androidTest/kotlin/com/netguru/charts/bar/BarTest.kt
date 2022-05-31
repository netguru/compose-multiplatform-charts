package com.netguru.charts.bar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import com.karumi.shot.ScreenshotTest
import com.netguru.charts.Util.checkComposable
import com.netguru.charts.theme.ChartDefaults
import org.junit.Rule
import org.junit.Test

class BarTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    /********************************
     * zero values
     ********************************/

//    @Test
//    fun oneCategory_oneBar_zeroValues() { todo! this fails rn - BN-3899
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
        val data = Data.generateData(1, 1, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun oneCategory_twoBars_positiveValues() {
        val data = Data.generateData(1, 2, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun oneCategory_fiveBars_positiveValues() {
        val data = Data.generateData(1, 5, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun twoCategories_oneBar_positiveValues() {
        val data = Data.generateData(2, 1, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun twoCategories_twoBars_positiveValues() {
        val data = Data.generateData(2, 2, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun twoCategories_fiveBars_positiveValues() {
        val data = Data.generateData(2, 5, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun fiveCategories_oneBar_positiveValues() {
        val data = Data.generateData(5, 1, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun fiveCategories_twoBars_positiveValues() {
        val data = Data.generateData(5, 2, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun fiveCategories_fiveBars_positiveValues() {
        val data = Data.generateData(5, 5, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun oneCategory_oneBar_positiveValues_customUI() {
        val data = Data.generateData(1, 1, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChart(
                data = data,
                chartColors = ChartDefaults.chartColors(grid = Color.Red),
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
     * negative values
     ********************************/

    @Test
    fun oneCategory_oneBar_negativeValues() {
        val data = Data.generateData(1, 1, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun oneCategory_twoBars_negativeValues() {
        val data = Data.generateData(1, 2, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun oneCategory_fiveBars_negativeValues() {
        val data = Data.generateData(1, 5, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun twoCategories_oneBar_negativeValues() {
        val data = Data.generateData(2, 1, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun twoCategories_twoBars_negativeValues() {
        val data = Data.generateData(2, 2, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun twoCategories_fiveBars_negativeValues() {
        val data = Data.generateData(2, 5, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun fiveCategories_oneBar_negativeValues() {
        val data = Data.generateData(5, 1, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun fiveCategories_twoBars_negativeValues() {
        val data = Data.generateData(5, 2, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun fiveCategories_fiveBars_negativeValues() {
        val data = Data.generateData(5, 5, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun oneCategory_oneBar_negativeValues_customUI() {
        val data = Data.generateData(1, 1, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChart(
                data = data,
                chartColors = ChartDefaults.chartColors(grid = Color.Red),
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
     * positive and negative values
     ********************************/

    @Test
    fun oneCategory_twoBars_positiveAndNegativeValues() {
        val data = Data.generateData(1, 2, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun oneCategory_fiveBars_positiveAndNegativeValues() {
        val data = Data.generateData(1, 5, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun twoCategories_twoBars_positiveAndNegativeValues() {
        val data = Data.generateData(2, 2, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun twoCategories_fiveBars_positiveAndNegativeValues() {
        val data = Data.generateData(2, 5, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun fiveCategories_twoBars_positiveAndNegativeValues() {
        val data = Data.generateData(5, 2, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun fiveCategories_fiveBars_positiveAndNegativeValues() {
        val data = Data.generateData(5, 5, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChart(data = data)
        }
    }

    @Test
    fun oneCategory_twoBars_positiveAndNegativeValues_customUI() {
        val data = Data.generateData(1, 2, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChart(
                data = data,
                chartColors = ChartDefaults.chartColors(grid = Color.Red),
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
}