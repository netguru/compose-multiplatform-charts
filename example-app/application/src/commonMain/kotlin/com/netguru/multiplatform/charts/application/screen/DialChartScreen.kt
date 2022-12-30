package com.netguru.multiplatform.charts.application.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.application.ScrollableScreen
import com.netguru.multiplatform.charts.application.SpacedColumn
import com.netguru.multiplatform.charts.application.TitleText
import com.netguru.multiplatform.charts.common.AppTheme
import com.netguru.multiplatform.charts.common.HorizontalDivider
import com.netguru.multiplatform.charts.dial.Dial
import com.netguru.multiplatform.charts.dial.DialColors
import com.netguru.multiplatform.charts.dial.DialConfig
import com.netguru.multiplatform.charts.dial.DialJoinStyle
import com.netguru.multiplatform.charts.dial.DialProgressColors
import com.netguru.multiplatform.charts.dial.PercentageDial
import com.netguru.multiplatform.charts.dial.scale.MarkType
import com.netguru.multiplatform.charts.dial.scale.Scale
import com.netguru.multiplatform.charts.dial.scale.Scale.NonLinear.ScalePoint
import kotlin.math.roundToInt

@Composable
fun DialChartScreen() {
    ScrollableScreen {
        SpacedColumn {
            TitleText(text = "Percentage dial")
            var sliderValue by remember {
                mutableStateOf(-50f)
            }
            PercentageDial(
                percentage = sliderValue,
                modifier = Modifier
                    .fillMaxWidth(),
                animation = ChartAnimation.Disabled,
                colors = DialColors(
                    progressBarBackgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                    gridScaleColor = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                    progressBarColor = DialProgressColors.GradientWithStops(
                        listOf(
                            0.5f to Color.Red,
                            1f to Color.Green,
                        )
                    )
                ),
                config = DialConfig(
                    thickness = 20.dp,
                    roundCorners = true,
                    joinStyle = DialJoinStyle.Overlapped,
                    scalePadding = 16.dp,
                    scaleLineLength = 10.dp,
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
                },
                indicator = {
                    Image(
                        painter = rememberVectorPainter(image = Arrow),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(
                                start = 20.dp,
                            )
                            .rotate(-90f)
                    )
                },
                scale = Scale.NonLinear(
                    scalePoints = listOf(
                        ScalePoint(0f, 0f, "0"),
                        ScalePoint(10f, 0.1f, "10"),
                        ScalePoint(20f, 0.2f, "20"),
                        ScalePoint(30f, 0.3f, "30"),
                        ScalePoint(35f, 0.5f, "35"),
                        ScalePoint(40f, 0.7f, "40"),
                        ScalePoint(100f, 1f, "100"),
                    ),
                    scaleLabel = {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    },
                    markType = MarkType.Dot,
                ),
                minAndMaxValueLabel = null,
            )

            Dial(
                value = Float.POSITIVE_INFINITY,
                modifier = Modifier
                    .fillMaxWidth(),
                animation = ChartAnimation.Disabled,
                colors = DialColors(
                    progressBarBackgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                    gridScaleColor = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                    progressBarColor = DialProgressColors.GradientWithStops(
                        listOf(
                            0.5f to Color.Red,
                            1f to Color.Green,
                        )
                    )
                ),
                config = DialConfig(
                    thickness = 20.dp,
                    roundCorners = true,
                    joinStyle = DialJoinStyle.Overlapped,
                    scalePadding = 16.dp,
                    scaleLineLength = 10.dp,
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
                },
                indicator = {
                    Image(
                        painter = rememberVectorPainter(image = Arrow),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(
                                start = 20.dp,
                            )
                            .rotate(-90f)
                    )
                },
                scale = Scale.NonLinear(
                    scalePoints = listOf(
                        ScalePoint(0f, 0f, "LO"),
                        ScalePoint(34f, 0.2f, "3.4"),
                        ScalePoint(47f, 0.4f, "4.7"),
                        ScalePoint(61f, 0.6f, "6.1"),
                        ScalePoint(73f, 0.8f, "7.3"),
                        ScalePoint(80f, 1f, "HI"),
                    ),
                    scaleLabel = {
                        Text(
                            text = it,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    },
                    markType = MarkType.Dot,
                ),
                minAndMaxValueLabel = null,
                minValue = Float.MIN_VALUE,
                maxValue = Float.MAX_VALUE,
            )

            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it.roundToInt().toFloat()
                },
                valueRange = -50f..150f,
                steps = 101,
                modifier = Modifier
                    .fillMaxWidth()
            )

            PercentageDial(
                percentage = sliderValue,
                modifier = Modifier
                    .fillMaxWidth(),
                animation = ChartAnimation.Disabled,
                config = DialConfig(
                    thickness = 20.dp,
                    roundCorners = true,
                    joinStyle = DialJoinStyle.Overlapped,
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
                },
                indicator = {
                    Box(
                        modifier = Modifier
                            .background(Color.Red)
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                }
            )

            PercentageDial(
                percentage = Float.POSITIVE_INFINITY,
                modifier = Modifier
                    .fillMaxWidth(),
                animation = ChartAnimation.Disabled,
                config = DialConfig(
                    thickness = 20.dp,
                    roundCorners = true,
                    joinStyle = DialJoinStyle.Overlapped,
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
                value = 17f,
                minValue = -20f,
                maxValue = 50f,
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
