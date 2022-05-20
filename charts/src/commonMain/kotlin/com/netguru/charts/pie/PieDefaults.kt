package com.netguru.charts.pie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.netguru.charts.round

internal object PieDefaults {
    const val FULL_CIRCLE_DEGREES = 360f
    const val START_ANGLE = 270.0
    const val NUMBER_OF_COLS_IN_LEGEND = 4

    val LegendItemLabel: @Composable (PieChartData) -> Unit = {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = it.value.round(1)
            )

            Text(
                text = it.name,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}
