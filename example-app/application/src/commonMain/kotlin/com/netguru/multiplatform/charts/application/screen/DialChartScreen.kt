package com.netguru.multiplatform.charts.application.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.application.PresentedItem
import com.netguru.multiplatform.charts.common.AppTheme
import com.netguru.multiplatform.charts.dial.Dial
import com.netguru.multiplatform.charts.dial.DialChartColors
import com.netguru.multiplatform.charts.dial.DialConfig
import com.netguru.multiplatform.charts.dial.DialJoinStyle
import com.netguru.multiplatform.charts.dial.DialProgressColors
import com.netguru.multiplatform.charts.dial.scale.MarkType
import com.netguru.multiplatform.charts.dial.scale.ScaleConfig
import com.netguru.multiplatform.charts.line.Progression
import com.netguru.multiplatform.charts.line.Progression.NonLinear.AnchorPoint
import kotlin.math.roundToInt

@Composable
fun DialChartScreen() {
    Column {
        var sliderValue by remember {
            mutableStateOf(-50f)
        }
        val commonModifier = Modifier
            .padding(20.dp)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(350.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                PresentedItem(text = "Percentage dial") {
                    Dial(
                        value = sliderValue,
                        modifier = commonModifier,
                        animation = ChartDisplayAnimation.Disabled,
                        colors = DialChartColors(
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
                        ),
                        minAndMaxValueLabel = null,
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
                    )
                }
            }

            item {
                PresentedItem(text = "Non-linear scale, simple gradient") {
                    Dial(
                        value = sliderValue,
                        modifier = commonModifier,
                        animation = ChartDisplayAnimation.Disabled,
                        colors = DialChartColors(
                            progressBarBackgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                            gridScaleColor = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                            progressBarColor = DialProgressColors.Gradient(
                                listOf(
                                    Color.Red,
                                    Color.Green,
                                )
                            )
                        ),
                        config = DialConfig(
                            thickness = 20.dp,
                            roundCorners = true,
                            joinStyle = DialJoinStyle.WithDegreeGap(15f),
                        ),
                        progression = Progression.NonLinear(
                            anchorPoints = listOf(
                                AnchorPoint(0f, 0f),
                                AnchorPoint(20f, 0.1f),
                                AnchorPoint(40f, 0.2f),
                                AnchorPoint(60f, 0.3f),
                                AnchorPoint(80f, 0.8f),
                                AnchorPoint(100f, 0.9f),
                                AnchorPoint(120f, 1f),
                            )
                        ),
                        scaleConfig = ScaleConfig.NonLinearProgressionConfig(
                            scalePadding = 16.dp,
                            scaleLineLength = 10.dp,
                            scaleLabelLayout = {
                                Text(
                                    text = it.toString(),
                                    modifier = Modifier
                                        .padding(16.dp)
                                )
                            },
                            markType = MarkType.Dot,
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
                        },
                        minAndMaxValueLabel = null,
                        minValue = Float.MIN_VALUE,
                        maxValue = Float.MAX_VALUE,
                    )
                }
            }

            item {
                PresentedItem(text = "Non-linear scale, advanced gradient") {
                    Dial(
                        value = sliderValue,
                        modifier = commonModifier,
                        animation = ChartDisplayAnimation.Disabled,
                        colors = DialChartColors(
                            progressBarBackgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                            gridScaleColor = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                            progressBarColor = DialProgressColors.GradientWithStops(
                                listOf(
                                    0f to Color.Red,
                                    0.1f to Color.Green,
                                    0.2f to Color.Blue,
                                    0.3f to Color.Yellow,
                                    0.8f to Color.White,
                                    0.9f to Color.Magenta,
                                    1f to Color.Cyan,
                                )
                            )
                        ),
                        config = DialConfig(
                            thickness = 20.dp,
                            roundCorners = true,
                            joinStyle = DialJoinStyle.Joined,
                        ),
                        progression = Progression.NonLinear(
                            anchorPoints = listOf(
                                AnchorPoint(0f, 0f),
                                AnchorPoint(20f, 0.1f),
                                AnchorPoint(40f, 0.2f),
                                AnchorPoint(60f, 0.3f),
                                AnchorPoint(80f, 0.8f),
                                AnchorPoint(100f, 0.9f),
                                AnchorPoint(120f, 1f),
                            )
                        ),
                        scaleConfig = ScaleConfig.NonLinearProgressionConfig(
                            scalePadding = 16.dp,
                            scaleLineLength = 10.dp,
                            scaleLabelLayout = {
                                Text(
                                    text = it.toString(),
                                    modifier = Modifier
                                        .padding(16.dp)
                                )
                            },
                            markType = MarkType.Dot,
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
                        },
                        minAndMaxValueLabel = null,
                        minValue = Float.MIN_VALUE,
                        maxValue = Float.MAX_VALUE,
                    )
                }
            }

            item {
                PresentedItem("No indicator, bigger angle") {
                    Dial(
                        value = sliderValue,
                        minValue = -50f,
                        maxValue = 150f,
                        modifier = commonModifier,
                        animation = ChartDisplayAnimation.Disabled,
                        config = DialConfig(
                            thickness = 20.dp,
                            roundCorners = true,
                            joinStyle = DialJoinStyle.Overlapped,
                            fullAngleInDegrees = 260f,
                        ),
                        mainLabel = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "$it°C",
                                    style = MaterialTheme.typography.h4,
                                    color = AppTheme.colors.yellow
                                )
                            }
                        },
                        scaleConfig = ScaleConfig.LinearProgressionConfig(
                            smallMarkStep = 10f,
                            bigMarkStep = 50f,
                            scaleLabelLayout = {
                                Text(
                                    text = it.toString(),
                                    modifier = Modifier
                                        .padding(10.dp)
                                )
                            }
                        ),
                    )
                }
            }

            item {
                PresentedItem(text = "Non-round corners") {
                    Dial(
                        modifier = commonModifier,
                        value = sliderValue,
                        minValue = -50f,
                        maxValue = 150f,
                        animation = ChartDisplayAnimation.Disabled,
                        config = DialConfig(
                            thickness = 30.dp,
                        ),
                        mainLabel = {
                            Text(
                                text = "$it°C",
                                style = MaterialTheme.typography.h4,
                                color = AppTheme.colors.yellow,
                            )
                        }
                    )
                }
            }

            item {
                PresentedItem(text = "No scale") {
                    Dial(
                        modifier = commonModifier,
                        value = sliderValue,
                        minValue = -50f,
                        maxValue = 150f,
                        animation = ChartDisplayAnimation.Disabled,
                        config = DialConfig(
                            thickness = 30.dp,
                        ),
                        mainLabel = {
                            Text(
                                text = "$it°C",
                                style = MaterialTheme.typography.h4,
                                color = AppTheme.colors.yellow,
                            )
                        },
                        scaleConfig = null,
                    )
                }
            }

            item(
                span = { GridItemSpan(Int.MAX_VALUE) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Set the value for dials above",
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
                            .weight(1f)
                    )
                }
            }

            item {
                PresentedItem(text = "Animated") {
                    Dial(
                        modifier = commonModifier,
                        value = 6f,
                        minValue = 0f,
                        maxValue = 10f,
                        animation = ChartDisplayAnimation.Simple {
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
    }
}
