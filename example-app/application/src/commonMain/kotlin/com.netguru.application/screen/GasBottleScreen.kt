package com.netguru.application.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.gasBottleChart.GasBottle

@Composable
fun GasBottleChartScreen() {

    val gasBottleModifier = Modifier
        .size(300.dp)

    SpacedColumn {
        TitleText(text = "Gas bottle chart")

        listOf(0, 25, 50, 75, 100).forEach { percentage ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Percentage: $percentage%")
                GasBottle(
                    percentage = percentage,
                    modifier = gasBottleModifier,
                )
            }
        }
    }
}
