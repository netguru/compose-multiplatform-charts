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

class BarWithLegendTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    /********************************
     * zero values
     ********************************/

//    @Test
//    fun oneCategory_oneBar_zeroValues() { todo! this fails rn - BN-3899
//        val data = Data.generateData(1, 1, Data.ValueTypes.ZERO)
//        checkComposable(composeRule) {
//            BarChartWithLegend(data = data)
//        }
//    }

    /********************************
     * positive values
     ********************************/

    @Test
    fun oneCategory_oneBar_positiveValues() {
        val data = Data.generateData(1, 1, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun oneCategory_twoBars_positiveValues() {
        val data = Data.generateData(1, 2, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun oneCategory_tenBars_positiveValues() {
        val data = Data.generateData(1, 10, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun twoCategories_oneBar_positiveValues() {
        val data = Data.generateData(2, 1, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun twoCategories_twoBars_positiveValues() {
        val data = Data.generateData(2, 2, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun twoCategories_tenBars_positiveValues() {
        val data = Data.generateData(2, 10, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun tenCategories_oneBar_positiveValues() {
        val data = Data.generateData(10, 1, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun tenCategories_twoBars_positiveValues() {
        val data = Data.generateData(10, 2, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun tenCategories_tenBars_positiveValues() {
        val data = Data.generateData(10, 10, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun oneCategory_oneBar_positiveValues_customUI() {
        val data = Data.generateData(1, 1, Data.ValueTypes.POSITIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(
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
                legendItemLabel = {
                    Text(text = "Custom label for: $it")
                }
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
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun oneCategory_twoBars_negativeValues() {
        val data = Data.generateData(1, 2, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun oneCategory_tenBars_negativeValues() {
        val data = Data.generateData(1, 10, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun twoCategories_oneBar_negativeValues() {
        val data = Data.generateData(2, 1, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun twoCategories_twoBars_negativeValues() {
        val data = Data.generateData(2, 2, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun twoCategories_tenBars_negativeValues() {
        val data = Data.generateData(2, 10, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun tenCategories_oneBar_negativeValues() {
        val data = Data.generateData(10, 1, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun tenCategories_twoBars_negativeValues() {
        val data = Data.generateData(10, 2, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun tenCategories_tenBars_negativeValues() {
        val data = Data.generateData(10, 10, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun oneCategory_oneBar_negativeValues_customUI() {
        val data = Data.generateData(1, 1, Data.ValueTypes.NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(
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
                legendItemLabel = {
                    Text(text = "Custom label for: $it")
                }
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
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun oneCategory_tenBars_positiveAndNegativeValues() {
        val data = Data.generateData(1, 10, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun twoCategories_twoBars_positiveAndNegativeValues() {
        val data = Data.generateData(2, 2, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun twoCategories_tenBars_positiveAndNegativeValues() {
        val data = Data.generateData(2, 10, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun tenCategories_twoBars_positiveAndNegativeValues() {
        val data = Data.generateData(10, 2, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun tenCategories_tenBars_positiveAndNegativeValues() {
        val data = Data.generateData(10, 10, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(data = data)
        }
    }

    @Test
    fun oneCategory_twoBars_positiveAndNegativeValues_customUI() {
        val data = Data.generateData(1, 2, Data.ValueTypes.POSITIVE_AND_NEGATIVE)
        checkComposable(composeRule) {
            BarChartWithLegend(
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
                legendItemLabel = {
                    Text(text = "Custom label for: $it")
                }
            )
        }
    }
}