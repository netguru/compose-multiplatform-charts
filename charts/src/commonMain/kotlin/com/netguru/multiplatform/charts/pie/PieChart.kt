package com.netguru.multiplatform.charts.pie

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.StartAnimation
import com.netguru.multiplatform.charts.mapValueToDifferentRange
import com.netguru.multiplatform.charts.pie.PieDefaults.FULL_CIRCLE_DEGREES
import com.netguru.multiplatform.charts.pie.PieDefaults.START_ANGLE
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

data class PieChartData(val name: String, val value: Double, val color: Color)

/**
 * Donut shaped 2D pie chart, portraying the values based on the ratios between them.
 *
 * This chart uses no labels to describe the data. In case labels are needed, use
 * [PieChartWithLegend]
 *
 * @param data Data to show
 * @param animation Animation to use. [ChartAnimation.Sequenced] is currently not supported and will
 * @param config The parameters for chart appearance customization
 * throw an [kotlin.UnsupportedOperationException] if used.
 *
 * @throws kotlin.UnsupportedOperationException when [ChartAnimation.Sequenced] is used
 */
@Composable
fun PieChart(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    config: PieChartConfig = PieChartConfig(),
) {
    val animationPlayed = StartAnimation(animation, data)
    val maxAngle = when (animation) {
        ChartAnimation.Disabled -> {
            1f
        }
        is ChartAnimation.Simple -> {
            animateFloatAsState(
                targetValue = if (animationPlayed) FULL_CIRCLE_DEGREES else 0f,
                animationSpec = animation.animationSpec()
            ).value
        }
        is ChartAnimation.Sequenced -> {
            throw UnsupportedOperationException("ChartAnimation.Sequenced is currently not supported for PieChart!")
        }
    }

    val sumOfData by remember(data) {
        mutableStateOf(data.sumOf { it.value })
    }

    val sweepAngles by remember(maxAngle, data) {
        mutableStateOf(
            calculateSweepAngles(
                data = data,
                maxAngle = maxAngle,
                sumOfData = sumOfData
            )
        )
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .drawBehind {
                val clipPath = calculateClipPath(
                    angles = sweepAngles,
                    config = config,
                )
                clipPath(clipPath) {
                    var startAngle = START_ANGLE
                    sweepAngles.forEachIndexed { index, sweepAngle ->
                        drawArc(
                            color = data[index].color,
                            startAngle = startAngle.toFloat(),
                            sweepAngle = sweepAngle.toFloat(),
                            config = config,
                        )
                        startAngle += sweepAngle
                    }
                }
            }
    )
}

private fun calculateSweepAngles(data: List<PieChartData>, sumOfData: Double, maxAngle: Float) =
    data.map { pieChartData ->
        pieChartData.value.mapValueToDifferentRange(
            inMin = 0.0,
            inMax = sumOfData,
            outMin = 0.0,
            outMax = maxAngle.toDouble()
        )
    }

private fun DrawScope.drawArc(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    config: PieChartConfig,
) {
    val sizeMin = min(size.width, size.height)
    val strokeWidth = config.thickness.toPx().coerceAtMost(sizeMin / 2)

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(sizeMin - strokeWidth, sizeMin - strokeWidth),
        style = Stroke(
            width = strokeWidth,
        ),
        topLeft = Offset(strokeWidth / 2f, strokeWidth / 2f)
    )
}

private const val RIGHT_ANGLE = 90f
private const val HALF_CIRCLE_ANGLE = 180f
private const val FULL_CIRCLE_ANGLE = 360f

private fun DrawScope.calculateClipPath(
    angles: List<Double>,
    config: PieChartConfig,
): Path {
    val r = min(size.width, size.height) / 2f
    val gapWidth = config.gap.toPx()
    val chartPath = Path().apply { addRect(Rect(Offset.Zero, size)) }
    var angle = START_ANGLE
    val gapPaths = Path()

    angles.forEach {
        val p1 = findNextGapPoint(angle - RIGHT_ANGLE, gapWidth / 2f, center)
        val p2 = findNextGapPoint(angle, r, p1)
        val p3 = findNextGapPoint(angle + RIGHT_ANGLE, gapWidth, p2)
        val p4 = findNextGapPoint(angle - HALF_CIRCLE_ANGLE, r, p3)

        val gapPath = Path().apply {
            moveTo(center.x, center.y)
            lineTo(p1.x, p1.y)
            lineTo(p2.x, p2.y)
            lineTo(p3.x, p3.y)
            lineTo(p4.x, p4.y)
            close()
        }
        gapPaths.addPath(gapPath)
        angle += it
    }
    return Path().apply { op(chartPath, gapPaths, PathOperation.Difference) }
}

private fun findNextGapPoint(
    angle: Double,
    distance: Float,
    from: Offset,
): Offset {
    val t = angle * 2f * PI / FULL_CIRCLE_ANGLE
    return Offset(
        from.x + distance * cos(t).toFloat(),
        from.y + distance * sin(t).toFloat()
    )
}
