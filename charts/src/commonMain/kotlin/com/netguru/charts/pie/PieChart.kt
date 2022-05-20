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
import androidx.compose.ui.unit.dp
import com.netguru.charts.ChartAnimation
import com.netguru.charts.StartAnimation
import com.netguru.charts.mapValueToDifferentRange
import com.netguru.charts.pie.PieDefaults.FULL_CIRCLE_DEGREES
import com.netguru.charts.pie.PieDefaults.START_ANGLE
import kotlin.math.min

data class PieChartData(val name: String, val value: Double, val color: Color)

@Composable
fun PieChart(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
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
        modifier = modifier.aspectRatio(1f).drawBehind {
            var startAngle = START_ANGLE
            sweepAngles.forEachIndexed { index, sweepAngle ->
                drawArc(data[index].color, startAngle.toFloat(), sweepAngle.toFloat())
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
) {
    val padding = 48.dp.toPx()

    val sizeMin = min(size.width, size.height)

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        size = Size(sizeMin - padding, sizeMin - padding),
        style = Stroke(
            width = sizeMin / 10
        ),
        topLeft = Offset(padding / 2f, padding / 2f)
    )
}
