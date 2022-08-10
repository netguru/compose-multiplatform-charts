package com.netguru.application.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.netguru.application.ScrollableScreen
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.pie.LegendOrientation
import com.netguru.charts.pie.PieChart
import com.netguru.charts.pie.PieChartConfig
import com.netguru.charts.pie.PieChartData
import com.netguru.charts.pie.PieChartWithLegend
import com.netguru.common.AppTheme
import com.netguru.common.HorizontalDivider
import com.netguru.common.WindowSize
import kotlin.math.roundToInt

@Composable
fun PieChartScreen() {

    val data = (1..4).map {
        PieChartData(
            name = "Data $it",
            value = it.toDouble(),
            color = listOf(
                AppTheme.colors.yellow,
                AppTheme.colors.green,
                AppTheme.colors.blue,
                AppTheme.colors.danger,
            )[it % 4]
        )
    }
    val dataSum = data
        .map { it.value }
        .reduce { left, right -> left + right }
        .toFloat()

    ScrollableScreen {
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
                    .height(300.dp)
                    .fillMaxWidth(),
                config = PieChartConfig(
                    legendOrientation = legendOrientation,
                    legendPadding = AppTheme.dimens.grid_4,
                    legendIconSize = AppTheme.dimens.grid_2,
                ),
                legendItemLabel = LegendItem(dataSum = dataSum)
            )
        }
    }
}

private val legendOrientation
    @Composable
    get() = when (AppTheme.windowSize) {
        WindowSize.EXPANDED -> LegendOrientation.VERTICAL
        else -> LegendOrientation.HORIZONTAL
    }

@Composable
private fun LegendItem(dataSum: Float): @Composable (PieChartData) -> Unit = {
    val valueText = (100 * it.value / dataSum)
        .roundToInt()
        .toString()
        .plus("%")

    when (AppTheme.windowSize) {
        WindowSize.EXPANDED -> Row(
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.grid_0_5),
            modifier = Modifier
                .padding(horizontal = AppTheme.dimens.grid_0_5, vertical = AppTheme.dimens.grid_1)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            LegendItemLabel(it.name.plus(" - "))
            LegendItemValue(valueText)
        }
        else -> Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            LegendItemValue(valueText)
            LegendItemLabel(it.name)
        }
    }
}

@Composable
private fun LegendItemLabel(text: String) {
    Text(
        text = text,
        color = AppTheme.colors.primaryText
    )
}

@Composable
private fun LegendItemValue(text: String) {
    Text(
        text = text,
        color = AppTheme.colors.primaryText,
        fontWeight = FontWeight.Bold,
    )
}
