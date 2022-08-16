package com.netguru.application.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.netguru.application.ScrollableScreen
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.ChartAnimation
import com.netguru.charts.bar.BarChart
import com.netguru.charts.bar.BarChartCategory
import com.netguru.charts.bar.BarChartConfig
import com.netguru.charts.bar.BarChartData
import com.netguru.charts.bar.BarChartEntry
import com.netguru.charts.bar.BarChartWithLegend
import com.netguru.common.AppTheme
import com.netguru.common.HorizontalDivider

@Composable
fun BarChartScreen() {

    val data = BarChartData(
        categories = listOf(
            BarChartCategory(
                name = "Bar Chart 1",
                entries = listOf(
                    BarChartEntry(
                        x = "primary",
                        y = 17f,
                        color = AppTheme.colors.yellow,
                    ),
                    BarChartEntry(
                        x = "secondary",
                        y = 30f,
                        color = AppTheme.colors.green,
                    ),
                    BarChartEntry(
                        x = "tertiary",
                        y = 22f,
                        color = AppTheme.colors.blue,
                    ),
                )
            ),
            BarChartCategory(
                name = "Bar Chart 2",
                entries = listOf(
                    BarChartEntry(
                        x = "primary",
                        y = 25f,
                        color = AppTheme.colors.yellow,
                    ),
                    BarChartEntry(
                        x = "secondary",
                        y = 40f,
                        color = AppTheme.colors.green,
                    ),
                    BarChartEntry(
                        x = "tertiary",
                        y = 35f,
                        color = AppTheme.colors.blue,
                    ),
                )
            ),
            BarChartCategory(
                name = "Bar Chart 3",
                entries = listOf(
                    BarChartEntry(
                        x = "primary",
                        y = -8f,
                        color = AppTheme.colors.yellow,
                    ),
                    BarChartEntry(
                        x = "secondary",
                        y = -20f,
                        color = AppTheme.colors.green,
                    ),
                    BarChartEntry(
                        x = "tertiary",
                        y = -15f,
                        color = AppTheme.colors.blue,
                    ),
                )
            ),
        ),
    )

    ScrollableScreen {
        SpacedColumn {
            TitleText(text = "Bars")
            BarChart(
                data = data,
                modifier = Modifier.height(500.dp),
                yAxisLabel = {
                    Text(
                        text = it.toString(),
                        color = AppTheme.colors.secondaryText,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                xAxisLabel = {
                    Text(
                        text = it.toString(),
                        modifier = Modifier.padding(top = AppTheme.dimens.grid_2_5),
                        color = AppTheme.colors.secondaryText
                    )
                },
                animation = ChartAnimation.Sequenced()
            )

            HorizontalDivider()

            TitleText(text = "Bar chart with legend")
            BarChartWithLegend(
                data = data,
                config = BarChartConfig(
                    cornerRadius = 10.dp
                ),
                modifier = Modifier.height(500.dp),
                xAxisLabel = {
                    Text(
                        text = it.toString(),
                        modifier = Modifier.padding(top = AppTheme.dimens.grid_2_5),
                        color = AppTheme.colors.secondaryText
                    )
                },
                animation = ChartAnimation.Sequenced(),
                legendItemLabel = {
                    Text(
                        text = it,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
            )
        }
    }
}
