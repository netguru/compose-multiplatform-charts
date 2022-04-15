package com.netguru.charts.pie

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.netguru.charts.mapValueToDifferentRange
import kotlin.math.min

data class PieChartData(val name: String, val value: Double, val color: Color)

private const val DEFAULT_ANIMATION_DURATION_MS = 300
private const val DEFAULT_ANIMATION_DELAY_MS = 100
private const val FULL_CIRCLE_DEGREES = 360f
private const val START_ANGLE = 270.0

@Composable
fun PieChart(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    animate: Boolean = true,
    animationDuration: Int = DEFAULT_ANIMATION_DURATION_MS,
    animationDelay: Int = DEFAULT_ANIMATION_DELAY_MS,
) {
    var animationPlayed by remember(animate) {
        mutableStateOf(!animate)
    }

    val maxAngle by animateFloatAsState(
        targetValue = if (animationPlayed) FULL_CIRCLE_DEGREES else 0f,
        animationSpec = tween(durationMillis = animationDuration, delayMillis = animationDelay)
    )

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

    LaunchedEffect(key1 = true) {
        animationPlayed = true
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
    sweepAngle: Float
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
