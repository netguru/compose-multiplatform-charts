package com.netguru.multiplatform.charts.android.previews

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.bar.BarChartCategory
import com.netguru.multiplatform.charts.bar.BarChartData
import com.netguru.multiplatform.charts.bar.BarChartEntry
import com.netguru.multiplatform.charts.bar.BarChartWithLegend
import com.netguru.multiplatform.charts.common.AppTheme
import com.netguru.multiplatform.charts.common.WindowSize

@Preview(widthDp = 800, showBackground = true)
@Composable
fun BarChartPreview() {
    AppTheme(windowSize = WindowSize.EXPANDED) {
        BarChartWithLegend(
            data = barChartSampleData(),
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .padding(20.dp),
        )
    }
}

@Composable
fun barChartSampleData(): BarChartData {
    return BarChartData(
        categories = listOf(
            BarChartCategory(
                name = "Mon",
                entries = listOf(
                    BarChartEntry("Actual usage", 10f, AppTheme.colors.yellow),
                    BarChartEntry("Forecasted usage", 20f, AppTheme.colors.green)
                )
            ),
            BarChartCategory(
                name = "Tue",
                entries = listOf(
                    BarChartEntry("Actual usage", 15f, AppTheme.colors.yellow),
                    BarChartEntry("Forecasted usage", 10f, AppTheme.colors.green)
                )
            ),
            BarChartCategory(
                name = "Wed",
                entries = listOf(
                    BarChartEntry("Actual usage", 5f, AppTheme.colors.yellow),
                    BarChartEntry("Forecasted usage", 10f, AppTheme.colors.green)
                )
            ),
            BarChartCategory(
                name = "Thu",
                entries = listOf(
                    BarChartEntry("Actual usage", 10f, AppTheme.colors.yellow),
                    BarChartEntry("Forecasted usage", 10f, AppTheme.colors.green),
                )
            ),
            BarChartCategory(
                name = "Fri",
                entries = listOf(
                    BarChartEntry("Actual usage", 3f, AppTheme.colors.yellow),
                    BarChartEntry("Forecasted usage", 2f, AppTheme.colors.green)
                )
            ),
            BarChartCategory(
                name = "Sat",
                entries = listOf(
                    BarChartEntry("Actual usage", 0f, AppTheme.colors.yellow),
                    BarChartEntry("Forecasted usage", 2f, AppTheme.colors.green)
                )
            ),
            BarChartCategory(
                name = "Sun",
                entries = listOf(
                    BarChartEntry("Actual usage", 12f, AppTheme.colors.yellow),
                    BarChartEntry("Forecasted usage", 12f, AppTheme.colors.green)
                )
            ),
        ),
    )
}
