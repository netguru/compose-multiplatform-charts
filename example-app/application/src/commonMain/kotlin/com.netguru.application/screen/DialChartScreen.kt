package com.netguru.application.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.ChartAnimation
import com.netguru.charts.dial.Dial
import com.netguru.charts.dial.PercentageDial

@Composable
fun DialChartScreen() {

    SpacedColumn {
        TitleText(text = "Percentage dial")

        PercentageDial(
            percentage = 69,
            modifier = Modifier
                .size(300.dp),
            animation = ChartAnimation.Simple {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            },
            mainLabel = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "$it%", style = MaterialTheme.typography.h5, color = MaterialTheme.colors.secondary)
                    Text(text = "of people like numbers", style = MaterialTheme.typography.body2)
                }
            }
        )

        TitleText(text = "Custom ranged dial")

        Dial(
            modifier = Modifier
                .size(300.dp),
            value = 17,
            minValue = -20,
            maxValue = 50,
            animation = ChartAnimation.Simple {
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            },
            mainLabel = {
                Text(text = "$it°C")
            }
        )
    }
}
