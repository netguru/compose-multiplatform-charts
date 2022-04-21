package com.netguru.application.screen

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.pie.PieChart
import com.netguru.charts.pie.PieChartData
import com.netguru.charts.pie.PieChartWithLegend
import com.netguru.common.HorizontalDivider

@Composable
fun PieChartScreen() {

    val data = (1..4).map {
        PieChartData(
            name = "Data $it",
            value = it.toDouble(),
            color = listOf(
                Color.Red,
                Color.Green,
                Color.Blue,
                Color.Magenta,
            )[it % 4]
        )
    }

    SpacedColumn {

        TitleText(text = "Pie chart")
        PieChart(
            data = data,
            modifier = Modifier
                .size(300.dp)
        )

        HorizontalDivider()

        TitleText(text = "Pie chart with legend")
        PieChartWithLegend(
            pieChartData = data,
            modifier = Modifier
                .size(300.dp)
        )
    }
}