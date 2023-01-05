package com.netguru.multiplatform.charts.dial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.dial.scale.Scale
import com.netguru.multiplatform.charts.dial.scale.ScalePositions
import com.netguru.multiplatform.charts.dial.scale.drawScale
import com.netguru.multiplatform.charts.dial.scale.drawScaleLabels
import com.netguru.multiplatform.charts.getAnimationAlphas
import com.netguru.multiplatform.charts.mapValueToDifferentRange
import com.netguru.multiplatform.charts.theme.ChartTheme
import kotlin.math.PI
import kotlin.math.sin

private const val CIRCLE_ANGLE = 360f
internal const val MIN_ANGLE = -40f
internal const val MAX_ANGLE = 220f
internal const val START_ANGLE = MIN_ANGLE + 180f

internal fun getAspectRatio() = 1 / sin((MAX_ANGLE - MIN_ANGLE) / 4)

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
 * @param animation Animation to use. [ChartDisplayAnimation.Sequenced] throws an
 * [kotlin.UnsupportedOperationException], since there is only one value to display.
 * @param colors Colors to be used for the chart. [DialColors]
 * @param config The parameters for chart appearance customization.
 * @param minAndMaxValueLabel Composable to represent the [minValue] and [maxValue] on the bottom
 * left and right of the chart.
 * @param mainLabel Composable to show in the centre of the chart, showing the [value].
 *
 * @throws kotlin.UnsupportedOperationException when [ChartDisplayAnimation.Sequenced] is used
 */
@Composable
fun Dial(
    value: Float,
    minValue: Float,
    maxValue: Float,
    modifier: Modifier = Modifier,
    animation: ChartDisplayAnimation = ChartDisplayAnimation.Simple(),
    colors: DialColors = ChartTheme.colors.dialColors,
    config: DialConfig = DialConfig(),
    minAndMaxValueLabel: (@Composable (value: Float) -> Unit)? = DialDefaults.MinAndMaxValueLabel,
    mainLabel: @Composable (value: Float) -> Unit = DialDefaults.MainLabel,
    indicator: (@Composable () -> Unit)? = null,
    scale: Scale = Scale.Linear(null),
) {
    val animatedScale = getAnimationAlphas(
        animation = animation,
        numberOfElementsToAnimate = 1,
        uniqueDatasetKey = value,
    ).first()

    val targetProgress = value.coerceIn(minValue..maxValue) * animatedScale

    Box(modifier = modifier) {
        Column(modifier = Modifier.align(Alignment.Center)) {

            val fullAngle = MAX_ANGLE - MIN_ANGLE
            val sweepAngle = when (scale) {
                is Scale.Linear -> {
                    targetProgress.mapValueToDifferentRange(
                        minValue,
                        maxValue,
                        0f,
                        fullAngle
                    )
                }

                is Scale.NonLinear -> {
                    if (targetProgress.isNaN()) {
                        val range = scale.scalePoints.first() to scale.scalePoints[1]

                        minValue.mapValueToDifferentRange(
                            range.first.value,
                            range.second.value,
                            /*MIN_ANGLE + */(fullAngle * range.first.position),
                            fullAngle * range.second.position,
                        )

                    } else {
                        scale
                            .scalePoints
                            .zipWithNext()
                            .firstOrNull {
                                targetProgress in it.first.value..it.second.value
                            }
                            ?.let { range ->
                                targetProgress.mapValueToDifferentRange(
                                    range.first.value,
                                    range.second.value,
                                    fullAngle * range.first.position,
                                    fullAngle * range.second.position,
                                )
                            }
                            ?: run {
                                val isSmaller = targetProgress < scale.scalePoints.minOf { it.value }
                                if (isSmaller) {
                                    0f
                                } else {
                                    fullAngle
                                }
                            }
                    }
                }
            }

            val density = LocalDensity.current
            var angles by remember {
                mutableStateOf(
                    ScalePositions(
                        containerWidth = 0f,
                        containerCenterX = 0f,
                        offsets = emptyList(),
                    )
                )
            }
            BoxWithConstraints(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(getAspectRatio())
                    .drawBehind {
                        drawProgressBar(
                            value = targetProgress,
                            sweepAngle = sweepAngle,
                            minValue = minValue,
                            maxValue = maxValue,
                            config = config,
                            progressBarColor = colors.progressBarColor,
                            progressBarBackgroundColor = colors.progressBarBackgroundColor,
                        )

                        if (config.displayScale) {
                            val scaleCenter = Offset(
                                center.x,
                                size.width / 2 - (config.scaleLineWidth.toPx() / 2f)
                            )
                            if (!angles.calculatedFor(size.width, scaleCenter.x)) {
                                angles = ScalePositions(
                                    containerWidth = size.width,
                                    containerCenterX = scaleCenter.x,
                                    offsets = scale.calculateAngles(config, density, size.width, scaleCenter)
                                )
                            }
                            drawScale(
                                color = colors.gridScaleColor,
                                config = config,
                                markType = scale.markType,
                                calculatedAngles = angles.offsets,
                            )
                        }
                    }
            ) {
                Box(
                    modifier = Modifier
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(placeable.width, placeable.height) {
                                placeable.place(
                                    x = maxWidth.roundToPx() / 2 - placeable.width / 2,
                                    y = maxWidth.roundToPx() / 2 - placeable.height / 2
                                )
                            }
                        }
                ) {
                    mainLabel(value)
                }

                drawScaleLabels(scale, angles.offsets)

                if (indicator != null) {
                    Box(
                        modifier = Modifier
                            .width(maxWidth / 2)
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)
                                layout(placeable.width, placeable.height) {
                                    placeable.place(0, maxWidth.roundToPx() / 2 - placeable.height / 2)
                                }
                            }
                            .graphicsLayer(
                                rotationZ = sweepAngle + MIN_ANGLE,
                                transformOrigin = TransformOrigin(
                                    pivotFractionX = 1f,
                                    pivotFractionY = 0.5f,
                                )
                            )
                    ) {
                        indicator()
                    }
                }
            }
            if (minAndMaxValueLabel != null) {
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
}

private fun DrawScope.drawProgressBar(
    value: Float,
    sweepAngle: Float,
    minValue: Float,
    maxValue: Float,
    config: DialConfig,
    progressBarColor: DialProgressColors,
    progressBarBackgroundColor: Color,
) {
    val thickness = config.thickness.toPx()
    val radius = (size.width - thickness) / 2f
    val circumference = (2f * PI * radius).toFloat()
    val thicknessInDegrees = CIRCLE_ANGLE * thickness / circumference
    val arcPadding = if (config.roundCorners) thicknessInDegrees / 2f else 0f
    val topLeftOffset = Offset(thickness / 2f, thickness / 2f)
    // Arc has to be drawn on 2 * height space because we want only half of the circle
    val arcSize = Size(size.width - thickness, size.height * getAspectRatio() - thickness)
    val style = Stroke(
        width = thickness,
        cap = config.strokeCap,
        pathEffect = PathEffect.cornerPathEffect(20f)
    )

    val joinStyle = if (config.joinStyle != DialJoinStyle.Overlapped && (value == minValue || value == maxValue)) {
        DialJoinStyle.Joined
    } else {
        config.joinStyle
    }

    if (value < maxValue || !value.isFinite()) {
        drawArc(
            color = progressBarBackgroundColor,
            startAngle = MIN_ANGLE + 180 + joinStyle.startAnglePadding(sweepAngle, arcPadding),
            sweepAngle = ((MAX_ANGLE - MIN_ANGLE) - joinStyle.sweepAnglePadding(sweepAngle, arcPadding))
                .coerceAtLeast(0f),
            useCenter = false,
            style = style,
            topLeft = topLeftOffset,
            size = arcSize
        )
    }

    if (value >= minValue) {
        when (progressBarColor) {
            is DialProgressColors.Gradient -> {
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = progressBarColor.colors,
                        center = Offset(
                            x = size.width / 2,
                            y = size.height,
                        ),
                    ),
                    startAngle = START_ANGLE + arcPadding,
                    sweepAngle = (sweepAngle - (2f * arcPadding)).coerceAtLeast(0f),
                    useCenter = false,
                    style = style,
                    topLeft = topLeftOffset,
                    size = arcSize
                )
            }

            is DialProgressColors.GradientWithStops -> {
                drawArc(
                    brush = Brush.sweepGradient(
                        colorStops = progressBarColor.colorStops.toTypedArray(),
                        center = Offset(
                            x = size.width / 2,
                            y = size.height,
                        ),
                    ),
                    startAngle = START_ANGLE + arcPadding,
                    sweepAngle = (sweepAngle - (2f * arcPadding)).coerceAtLeast(0f),
                    useCenter = false,
                    style = style,
                    topLeft = topLeftOffset,
                    size = arcSize
                )
            }

            is DialProgressColors.SingleColor -> {
                drawArc(
                    color = progressBarColor.color,
                    startAngle = START_ANGLE + arcPadding,
                    sweepAngle = (sweepAngle - (2f * arcPadding)).coerceAtLeast(0f),
                    useCenter = false,
                    style = style,
                    topLeft = topLeftOffset,
                    size = arcSize
                )
            }
        }
    }
}

private val DialConfig.strokeCap: StrokeCap
    get() = if (roundCorners) StrokeCap.Round else StrokeCap.Butt

private fun DialJoinStyle.startAnglePadding(sweepAngle: Float, arcPadding: Float) = when (this) {
    is DialJoinStyle.Joined -> sweepAngle + (arcPadding)
    is DialJoinStyle.Overlapped -> arcPadding
    is DialJoinStyle.WithDegreeGap -> sweepAngle + (degrees + arcPadding)
}

private fun DialJoinStyle.sweepAnglePadding(sweepAngle: Float, arcPadding: Float) = when (this) {
    is DialJoinStyle.Joined -> sweepAngle + (2f * arcPadding)
    is DialJoinStyle.Overlapped -> 2f * arcPadding
    is DialJoinStyle.WithDegreeGap -> sweepAngle + (degrees + (2f * arcPadding))
}
