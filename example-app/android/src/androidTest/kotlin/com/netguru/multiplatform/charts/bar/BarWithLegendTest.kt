package com.netguru.multiplatform.charts.bar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.karumi.shot.ScreenshotTest
import com.netguru.multiplatform.charts.Util.checkComposable
import com.netguru.multiplatform.charts.theme.ChartDefaults
import org.junit.Rule
import org.junit.Test

class BarWithLegendTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Suppress("SameParameterValue")
    private fun testBasicChartWithLegend(
        nOfCategories: Int,
        nOfEntries: Int,
        valueTypes: Data.ValueTypes
    ) {
        val data = Data.generateData(nOfCategories, nOfEntries, valueTypes)
        checkComposable(composeRule) {
            BarChartWithLegend(
                data = data,
                config = BarChartConfig(
                    thickness = 8.dp,
                    cornerRadius = 0.dp,
                    barsSpacing = 1.dp,
                    maxHorizontalLinesCount = 2,
                )
            )
        }
    }

    @Suppress("SameParameterValue")
    private fun testCustomChartWithLegend(
        nOfCategories: Int,
        nOfEntries: Int,
        valueTypes: Data.ValueTypes
    ) {
        val data = Data.generateData(nOfCategories, nOfEntries, valueTypes)
        checkComposable(composeRule) {
            BarChartWithLegend(
                data = data,
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
                legendItemLabel = { name, unit ->
                    Text(text = "Custom label for: $name")
                }
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
//            BarChartWithLegend(data = data)
//        }
//    }

    /********************************
     * positive values
     ********************************/

    @Test
    fun oneCategory_oneBar_positiveValues() {
        testBasicChartWithLegend(1, 1, Data.ValueTypes.POSITIVE)
    }

    @Test
    fun oneCategory_fiveBars_positiveValues() {
        testBasicChartWithLegend(1, 5, Data.ValueTypes.POSITIVE)
    }

    @Test
    fun oneCategory_oneBar_positiveValues_customUI() {
        testCustomChartWithLegend(1, 1, Data.ValueTypes.POSITIVE)
    }
}
