package com.netguru.charts.dialChart

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import com.netguru.charts.dialChart.DialDefaults.GAP_DEGREE
import com.netguru.charts.dialChart.DialDefaults.SCALE_STROKE_WIDTH
import com.netguru.charts.dialChart.DialDefaults.START_ANGLE
import com.netguru.charts.dialChart.DialDefaults.THICKNESS
import com.netguru.charts.mapValueToDifferentRange
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

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
        BoxWithConstraints(
            Modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .align(Alignment.Center)
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
                            size.height / 2 + size.height / 4f
                        )
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = maxHeight / 4)
            ) {
                mainLabel(value)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = maxHeight / 8),
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
        topLeft = Offset(THICKNESS.toPx() / 2f, THICKNESS.toPx() / 2f + size.height / 4),
        size = Size(size.width - THICKNESS.toPx(), size.height - THICKNESS.toPx())
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
        topLeft = Offset(THICKNESS.toPx() / 2f, THICKNESS.toPx() / 2f + size.height / 4),
        size = Size(size.width - THICKNESS.toPx(), size.height - THICKNESS.toPx())
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
