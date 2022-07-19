package com.netguru.charts.dial

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.netguru.charts.ChartAnimation
import com.netguru.charts.StartAnimation
import com.netguru.charts.dial.DialDefaults.GAP_DEGREE
import com.netguru.charts.dial.DialDefaults.SCALE_STROKE_WIDTH
import com.netguru.charts.dial.DialDefaults.START_ANGLE
import com.netguru.charts.dial.DialDefaults.THICKNESS
import com.netguru.charts.mapValueToDifferentRange
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Draws a half-circle and colors the part of it differently to represent the value.
 *
 * The [minValue] and [maxValue] value of the chart are arbitrary. In case of [value] being below
 * [minValue] or above [maxValue], the drawing will be coerced to the min or max value (will not go
 * beyond the half-circle), but the [value] will still be provided to the [mainLabel] to be
 * displayed.
 *
 * @param value Value to portray.
 * @param minValue Min value of the chart (will also be provided to [minAndMaxValueLabel])
 * @param maxValue Max value of the chart (will also be provided to [minAndMaxValueLabel])
 * @param animation Animation to use. [ChartAnimation.Sequenced] throws an
 * [kotlin.UnsupportedOperationException], since there is only one value to display.
 * @param chartColors Colors to use for the chart. [ChartColors.primary] is used for the value part
 * of the chart, and [ChartColors.grid] is used for the rest of the chart and its grid scale.
 * @param minAndMaxValueLabel Composable to represent the [minValue] and [maxValue] on the bottom
 * left and right of the chart.
 * @param mainLabel Composable to show in the centre of the chart, showing the [value].
 *
 * @throws kotlin.UnsupportedOperationException when [ChartAnimation.Sequenced] is used
 */
@Composable
fun Dial(
    value: Int,
    minValue: Int,
    maxValue: Int,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    chartColors: ChartColors = ChartDefaults.chartColors(),
    minAndMaxValueLabel: @Composable (value: Int) -> Unit = DialDefaults.MinAndMaxValueLabel,
    mainLabel: @Composable (value: Int) -> Unit = DialDefaults.MainLabel,
) {
    val animationPlayed = StartAnimation(animation, value)
    val animatedScale = when (animation) {
        ChartAnimation.Disabled -> {
            1f
        }
        is ChartAnimation.Simple -> {
            animateFloatAsState(
                targetValue = if (animationPlayed) 1f else 0f,
                animationSpec = animation.animationSpec()
            ).value
        }
        is ChartAnimation.Sequenced -> {
            throw UnsupportedOperationException("As Dial chart only shows one value, ChartAnimation.Sequenced is not supported!")
        }
    }

    val targetProgress = value.coerceIn(minValue..maxValue) * animatedScale

    Box(modifier = modifier) {
        val progressBarColor = chartColors.primary
        val progressBarBackgroundColor = chartColors.grid
        val gridScaleColor = chartColors.grid
        Column(modifier = Modifier.align(Alignment.Center)) {
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .drawBehind {
                        drawProgressBar(
                            value = targetProgress,
                            minValue = minValue.toFloat(),
                            maxValue = maxValue.toFloat(),
                            progressBarColor = progressBarColor,
                            progressBarBackgroundColor = progressBarBackgroundColor,
                        )

                        drawScale(
                            color = gridScaleColor,
                            center = Offset(
                                size.width / 2f,
                                size.height
                            )
                        )
                    }
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = maxWidth / 4f)
                ) {
                    mainLabel(value)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                minAndMaxValueLabel(minValue)
                minAndMaxValueLabel(maxValue)
            }
        }
    }
}

private fun DrawScope.drawProgressBar(
    value: Float,
    minValue: Float,
    maxValue: Float,
    progressBarColor: Color,
    progressBarBackgroundColor: Color,
) {
    val sweepAngle = value.mapValueToDifferentRange(minValue, maxValue, 0f, 180f)
    drawArc(
        color = progressBarColor,
        startAngle = START_ANGLE,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = THICKNESS.toPx(),
            pathEffect = PathEffect.cornerPathEffect(20f)
        ),
        topLeft = Offset(THICKNESS.toPx() / 2f, THICKNESS.toPx() / 2f),
        size = Size(size.width - THICKNESS.toPx(), size.height * 2 - THICKNESS.toPx())
    )

    drawArc(
        color = progressBarBackgroundColor,
        startAngle = START_ANGLE + sweepAngle + GAP_DEGREE,
        sweepAngle = (180f - sweepAngle - GAP_DEGREE).coerceAtLeast(0f),
        useCenter = false,
        style = Stroke(
            width = THICKNESS.toPx(),
            pathEffect = PathEffect.cornerPathEffect(20f)
        ),
        topLeft = Offset(THICKNESS.toPx() / 2f, THICKNESS.toPx() / 2f),
        size = Size(size.width - THICKNESS.toPx(), size.height * 2 - THICKNESS.toPx())
    )
}

private const val START_RADIUS = 0.72f
private const val END_RADIUS = 0.74f
private fun DrawScope.drawScale(
    color: Color,
    center: Offset,
) {
    for (point in 0..100 step 2) {
        val angle = (
            point.toFloat()
                .mapValueToDifferentRange(
                    0f,
                    100f,
                    START_ANGLE,
                    0f
                )
            ) * PI.toFloat() / 180f // to radians
        val startRadius =
            size.width / 2 * if (point % 10 == 0) START_RADIUS - 0.02f else START_RADIUS
        val endRadius = size.width / 2 * if (point % 10 == 0) END_RADIUS + 0.02f else END_RADIUS
        val startPos = Offset(cos(angle) * startRadius, sin(angle) * startRadius)
        val endPos = Offset(cos(angle) * endRadius, sin(angle) * endRadius)
        drawLine(
            color = if (point % 10 == 0) color else color.copy(alpha = 0.5f),
            start = center + startPos,
            end = center + endPos,
            strokeWidth = SCALE_STROKE_WIDTH.toPx(),
            cap = StrokeCap.Round
        )
    }
}
