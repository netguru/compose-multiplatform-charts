package com.netguru.application.screen

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.dialChart.PercentageDial

@Composable
fun DialChartScreen() {

    SpacedColumn {
        TitleText(text = "Percentage dial")

        PercentageDial(
            percentage = 69,
            text = "of people like numbers",
            modifier = Modifier
                .size(300.dp)
        )
    }
}