package com.netguru.multiplatform.charts.application.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.application.ScrollableScreen
import com.netguru.multiplatform.charts.application.SpacedColumn
import com.netguru.multiplatform.charts.application.TitleText
import com.netguru.multiplatform.charts.common.AppTheme
import com.netguru.multiplatform.charts.common.HorizontalDivider
import com.netguru.multiplatform.charts.dial.Dial
import com.netguru.multiplatform.charts.dial.DialConfig
import com.netguru.multiplatform.charts.dial.PercentageDial

@Composable
fun DialChartScreen() {
    ScrollableScreen {
        SpacedColumn {
            TitleText(text = "Percentage dial")

            PercentageDial(
                percentage = 69,
                modifier = Modifier
                    .fillMaxWidth(),
                animation = ChartAnimation.Simple {
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                },
                config = DialConfig(
                    thickness = 20.dp,
                    roundCorners = true,
                ),
                mainLabel = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "$it%",
                            style = MaterialTheme.typography.h4,
                            color = AppTheme.colors.yellow
                        )
                        Text(
                            text = "of people like numbers",
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(vertical = AppTheme.dimens.grid_2)
                        )
                    }
                }
            )

            HorizontalDivider()

            TitleText(text = "Custom ranged dial")

            Dial(
                modifier = Modifier
                    .fillMaxWidth(),
                value = 17,
                minValue = -20,
                maxValue = 50,
                animation = ChartAnimation.Simple {
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                },
                config = DialConfig(
                    thickness = 30.dp,
                ),
                mainLabel = {
                    Text(
                        text = "$it°C",
                        style = MaterialTheme.typography.h4,
                        color = AppTheme.colors.yellow
                    )
                }
            )
        }
    }
}
