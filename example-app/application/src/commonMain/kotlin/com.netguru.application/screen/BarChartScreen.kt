package com.netguru.application.screen

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.barchart.BarChart
import com.netguru.charts.barchart.BarChartCategory
import com.netguru.charts.barchart.BarChartData
import com.netguru.charts.barchart.BarChartEntry
import com.netguru.charts.barchart.BarChartWithLegend
import com.netguru.charts.round
import com.netguru.common.HorizontalDivider

@Composable
fun BarChartScreen() {

    val data = BarChartData(
        categories = listOf(
            BarChartCategory(
                name = "firstCat",
                entries = listOf(
                    BarChartEntry(
                        x = "primary",
                        y = 10f,
                        color = Color.Red,
                    ),
                    BarChartEntry(
                        x = "secondary",
                        y = 20f,
                        color = Color.Green,
                    ),
                    BarChartEntry(
                        x = "tertiary",
                        y = 15f,
                        color = Color.Blue,
                    ),
                )
            ),
            BarChartCategory(
                name = "secondCat",
                entries = listOf(
                    BarChartEntry(
                        x = "primary",
                        y = 30f,
                        color = Color.Red,
                    ),
                    BarChartEntry(
                        x = "secondary",
                        y = 40f,
                        color = Color.Green,
                    ),
                    BarChartEntry(
                        x = "tertiary",
                        y = 35f,
                        color = Color.Blue,
                    ),
                )
            ),
            BarChartCategory(
                name = "thirdCat",
                entries = listOf(
                    BarChartEntry(
                        x = "primary",
                        y = 20f,
                        color = Color.Red,
                    ),
                    BarChartEntry(
                        x = "secondary",
                        y = 30f,
                        color = Color.Green,
                    ),
                    BarChartEntry(
                        x = "tertiary",
                        y = 25f,
                        color = Color.Blue,
                    ),
                )
            ),
        ),
    )

    SpacedColumn {

        TitleText(text = "Bar chart")
        BarChart(
            data = data,
            modifier = Modifier
                .height(300.dp),
        )

        HorizontalDivider()

        TitleText(text = "Bar chart with legend")
        BarChartWithLegend(
            data = data,
            modifier = Modifier
                .height(300.dp)
        )

    }
}
