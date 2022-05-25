package com.netguru.application.screen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.ChartAnimation
import com.netguru.charts.bar.BarChart
import com.netguru.charts.bar.BarChartCategory
import com.netguru.charts.bar.BarChartData
import com.netguru.charts.bar.BarChartEntry
import com.netguru.charts.bar.BarChartWithLegend
import com.netguru.common.HorizontalDivider

@Composable
fun BarChartScreen() {

    val data = remember {
        BarChartData(
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
                            y = -5f,
                            color = Color.Red,
                        ),
                        BarChartEntry(
                            x = "secondary",
                            y = -15f,
                            color = Color.Green,
                        ),
                        BarChartEntry(
                            x = "tertiary",
                            y = -10f,
                            color = Color.Blue,
                        ),
                    )
                ),
            ),
        )
    }

    SpacedColumn {

        TitleText(text = "Bar chart")
        BarChart(
            data = data,
            modifier = Modifier
                .height(300.dp),
            yAxisLabel = {
                Text(text = it.toString())
            },
            xAxisLabel = {
                Text(
                    text = it.toString(),
                    modifier = Modifier.padding(top = 20.dp)
                )
            },
            animation = ChartAnimation.Sequenced()
        )

        HorizontalDivider()

        TitleText(text = "Bar chart with legend")
        BarChartWithLegend(
            data = data,
            modifier = Modifier
                .height(300.dp),
            animation = ChartAnimation.Sequenced()
        )
    }
}
