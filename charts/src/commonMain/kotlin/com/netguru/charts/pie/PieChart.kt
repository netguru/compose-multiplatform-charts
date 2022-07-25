package com.netguru.charts.pie

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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.netguru.charts.ChartAnimation
import com.netguru.charts.StartAnimation
import com.netguru.charts.mapValueToDifferentRange
import com.netguru.charts.pie.PieDefaults.FULL_CIRCLE_DEGREES
import com.netguru.charts.pie.PieDefaults.START_ANGLE
import kotlin.math.min

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
