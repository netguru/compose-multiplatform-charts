package com.netguru.multiplatform.charts.android.previews

import android.graphics.PathEffect
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.common.AppTheme
import com.netguru.multiplatform.charts.common.HOUR_IN_MS
import com.netguru.multiplatform.charts.common.WindowSize
import com.netguru.multiplatform.charts.grid.axisscale.y.YAxisScaleDynamic
import com.netguru.multiplatform.charts.line.LineChart
import com.netguru.multiplatform.charts.line.LineChartData
import com.netguru.multiplatform.charts.line.LineChartPoint
import com.netguru.multiplatform.charts.line.LineChartSeries
import com.netguru.multiplatform.charts.line.XAxisConfig
import com.netguru.multiplatform.charts.line.YAxisConfig
import com.soywiz.klock.DateTime

@Preview(showBackground = true, widthDp = 600)
@Composable
fun LineChartPreview() {
    AppTheme(windowSize = WindowSize.EXPANDED) {
        val data = getLineChartSampleData()
        LineChart(
            data = data,
            yAxisConfig = YAxisConfig(
                scale = YAxisScaleDynamic(
                    chartData = data,
                )
            ),
            xAxisConfig = XAxisConfig(
                maxVerticalLines = 10,
            ),
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            displayAnimation = ChartDisplayAnimation.Disabled,
        )
    }
}

@Composable
private fun getLineChartSampleData(): LineChartData {
    val startTime = DateTime.now().milliseconds
    val list = mutableListOf<LineChartSeries>()

    list.add(
        LineChartSeries(
            "Solar",
            lineColor = AppTheme.colors.yellow,
            listOfPoints = listOf(
                LineChartPoint(0L * HOUR_IN_MS + startTime, 0f),
                LineChartPoint(1L * HOUR_IN_MS + startTime, 1f),
                LineChartPoint(2L * HOUR_IN_MS + startTime, 2f),
                LineChartPoint(3L * HOUR_IN_MS + startTime, 3f),
                LineChartPoint(5L * HOUR_IN_MS + startTime, 4f),
            )
        )
    )

    list.add(
        LineChartSeries(
            "Grid",
            lineColor = AppTheme.colors.green,
            listOfPoints = listOf(
                LineChartPoint(0L * HOUR_IN_MS + startTime, 3f),
                LineChartPoint(1L * HOUR_IN_MS + startTime, 2f),
                LineChartPoint(2L * HOUR_IN_MS + startTime, 1f),
                LineChartPoint(5L * HOUR_IN_MS + startTime, 3f),
            )
        )
    )

    list.add(
        LineChartSeries(
            "Fossil",
            lineColor = AppTheme.colors.blue,
            listOfPoints = listOf(
                LineChartPoint(0L * HOUR_IN_MS + startTime, 1f),
                LineChartPoint(1L * HOUR_IN_MS + startTime, 3f),
                LineChartPoint(2L * HOUR_IN_MS + startTime, 2f),
                LineChartPoint(3L * HOUR_IN_MS + startTime, 0f),
                LineChartPoint(5L * HOUR_IN_MS + startTime, 1f),
            )
        )
    )

    return LineChartData(
        series = list,
        dataUnit = null,
    )
}
