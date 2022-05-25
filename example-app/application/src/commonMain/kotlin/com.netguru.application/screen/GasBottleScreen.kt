package com.netguru.application.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.ChartAnimation
import com.netguru.charts.gasbottle.GasBottle

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
                    percentage = percentage.toFloat(),
                    modifier = gasBottleModifier,
                    animation = ChartAnimation.Simple {
                        spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessVeryLow
                        )
                    }
                )
            }
        }
    }
}
