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
import com.netguru.charts.dial.DialDefaults.START_ANGLE
import com.netguru.charts.mapValueToDifferentRange
import com.netguru.charts.theme.ChartTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Aspect ratio for dial is 2:1 (width:height). We want to draw only half of the circle
 */
private const val ASPECT_RATIO = 2f
private const val CIRCLE_ANGLE = 360f
private const val MIN_ANGLE = 0f
private const val MAX_ANGLE = 180f

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
 * @param colors Colors to be used for the chart. [DialColors]
 * @param config The parameters for chart appearance customization.
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
    colors: DialColors = ChartTheme.colors.dialColors,
    config: DialConfig = DialConfig(),
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
        Column(modifier = Modifier.align(Alignment.Center)) {
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(ASPECT_RATIO)
                    .drawBehind {
                        drawProgressBar(
                            value = targetProgress,
                            minValue = minValue.toFloat(),
                            maxValue = maxValue.toFloat(),
                            config = config,
                            progressBarColor = colors.progressBarColor,
                            progressBarBackgroundColor = colors.progressBarBackgroundColor,
                        )

                        if (config.displayScale) {
                            drawScale(
                                color = colors.gridScaleColor,
                                center = Offset(
                                    center.x,
                                    size.height - (config.scaleLineWidth.toPx() / 2f)
                                ),
                                config = config,
                            )
                        }
                    }
            ) {
                val desiredHeight = maxWidth / ASPECT_RATIO
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = desiredHeight / 2f)
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
    config: DialConfig,
    progressBarColor: Color,
    progressBarBackgroundColor: Color,
) {
    val sweepAngle = value.mapValueToDifferentRange(minValue, maxValue, MIN_ANGLE, MAX_ANGLE)
    val thickness = config.thickness.toPx()
    val radius = (size.width - thickness) / 2f
    val circumference = (2f * PI * radius).toFloat()
    val thicknessInDegrees = CIRCLE_ANGLE * thickness / circumference
    val arcPadding = if (config.roundCorners) thicknessInDegrees / 2f else 0f
    val topLeftOffset = Offset(thickness / 2f, thickness / 2f)
    // Arc has to be drawn on 2 * height space cause we want only half of the circle
    val arcSize = Size(size.width - thickness, size.height * ASPECT_RATIO - thickness)
    val style = Stroke(
        width = thickness,
        cap = config.strokeCap,
        pathEffect = PathEffect.cornerPathEffect(20f)
    )

    drawArc(
        color = progressBarColor,
        startAngle = START_ANGLE + arcPadding,
        sweepAngle = sweepAngle - (2f * arcPadding),
        useCenter = false,
        style = style,
        topLeft = topLeftOffset,
        size = arcSize
    )

    drawArc(
        color = progressBarBackgroundColor,
        startAngle = START_ANGLE + sweepAngle + config.joinStyle.startAnglePadding(arcPadding),
        sweepAngle = (MAX_ANGLE - sweepAngle - config.joinStyle.sweepAnglePadding(arcPadding))
            .coerceAtLeast(0f),
        useCenter = false,
        style = style,
        topLeft = topLeftOffset,
        size = arcSize
    )
}

private const val MAX_LINE_LENGTH = 0.20f
private const val MINOR_SCALE_ALPHA = 0.5f
private const val MINOR_SCALE_LENGTH_FACTOR = 0.35f
private const val SCALE_STEP = 2
private const val MAJOR_SCALE_MODULO = 5 * SCALE_STEP
private fun DrawScope.drawScale(
    color: Color,
    center: Offset,
    config: DialConfig,
) {
    val scaleLineLength = (config.scaleLineLength.toPx() / center.x).coerceAtMost(MAX_LINE_LENGTH)
    val scalePadding = (config.thickness.toPx() + config.scalePadding.toPx()) / center.x
    val startRadiusFactor = 1 - scalePadding - scaleLineLength
    val endRadiusFactor = startRadiusFactor + scaleLineLength
    val smallLineRadiusFactor = scaleLineLength * MINOR_SCALE_LENGTH_FACTOR
    val scaleMultiplier = size.width / 2f

    for (point in 0..100 step SCALE_STEP) {
        val angle = (
            point.toFloat()
                .mapValueToDifferentRange(
                    0f,
                    100f,
                    START_ANGLE,
                    0f
                )
            ) * PI.toFloat() / 180f // to radians
        val startPos = point.position(
            angle,
            scaleMultiplier,
            startRadiusFactor,
            smallLineRadiusFactor
        )
        val endPos = point.position(
            angle,
            scaleMultiplier,
            endRadiusFactor,
            -smallLineRadiusFactor
        )
        drawLine(
            color = if (point % MAJOR_SCALE_MODULO == 0)
                color
            else
                color.copy(alpha = MINOR_SCALE_ALPHA),
            start = center + startPos,
            end = center + endPos,
            strokeWidth = config.scaleLineWidth.toPx(),
            cap = StrokeCap.Round
        )
    }
}

private fun Int.position(
    angle: Float,
    scaleMultiplier: Float,
    radiusFactor: Float,
    minorRadiusFactor: Float
): Offset {
    val pointRadiusFactor = if (this % MAJOR_SCALE_MODULO == 0)
        radiusFactor
    else
        radiusFactor + minorRadiusFactor
    val scaledRadius = scaleMultiplier * pointRadiusFactor
    return Offset(cos(angle) * scaledRadius, sin(angle) * scaledRadius)
}

private val DialConfig.strokeCap: StrokeCap
    get() = if (roundCorners) StrokeCap.Round else StrokeCap.Butt

private fun DialJoinStyle.startAnglePadding(arcPadding: Float) = when (this) {
    is DialJoinStyle.Joined -> arcPadding
    is DialJoinStyle.Overlapped -> -2f * arcPadding
    is DialJoinStyle.WithDegreeGap -> degrees + arcPadding
}

private fun DialJoinStyle.sweepAnglePadding(arcPadding: Float) = when (this) {
    is DialJoinStyle.Joined -> 2f * arcPadding
    is DialJoinStyle.Overlapped -> -arcPadding
    is DialJoinStyle.WithDegreeGap -> degrees + (2f * arcPadding)
}
