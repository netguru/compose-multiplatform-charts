package com.netguru.charts.dialChart

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.netguru.charts.mapValueToDifferentRange
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val THICKNESS = 15.dp
private const val GAP_DEGREE = 2
private const val START_ANGLE = -180f
private val SCALE_STROKE_WIDTH = 2.dp

@Composable
fun PercentageDial(
    percentage: Int,
    text: String,
    modifier: Modifier = Modifier,
    animate: Boolean = true,
    chartColors: ChartColors = ChartDefaults.chartColors(),
    typography: Typography = MaterialTheme.typography,
) {
    Dial(
        modifier = modifier,
        percentage = percentage,
        animate = animate,
        chartColors = chartColors,
        typography = typography,
    ) {
        CenterDialContent(
            percentage = percentage,
            belowText = text,
            typography = typography,
            chartColors = chartColors,
        )
    }
}

@Composable
private fun Dial(
    modifier: Modifier,
    percentage: Int,
    animate: Boolean,
    chartColors: ChartColors,
    typography: Typography,
    content: @Composable BoxWithConstraintsScope.() -> Unit,
) {
    var animationPlayed by remember(animate) {
        mutableStateOf(!animate)
    }
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    val targetProgress by animateIntAsState(
        targetValue = if (animationPlayed) percentage.coerceIn(0..100) else 0,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(modifier = modifier) {
        val progressBarColor = chartColors.primary
        val progressBarBackgroundColor = chartColors.borders
        val gridScaleColor = chartColors.grid
        BoxWithConstraints(
            Modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .align(Alignment.Center)
                .drawBehind
                {
                    drawProgressBar(
                        percentage = targetProgress
                            .coerceIn(0..100)
                            .toFloat(),
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
            content()

            // values 0 and 100 under dial
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = maxHeight / 8),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "0", style = typography.body2)
                Text(text = "100", style = typography.body2)
            }
        }
    }
}

@Composable
private fun BoxWithConstraintsScope.CenterDialContent(
    percentage: Int,
    belowText: String,
    typography: Typography,
    chartColors: ChartColors,
) {
    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .padding(top = maxHeight / 4),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = FormattedPercentageText(percentage, typography, chartColors))
        Text(text = belowText, style = typography.body2)
    }
}

@Composable
private fun FormattedPercentageText(
    percentage: Int,
    typography: Typography,
    chartColors: ChartColors,
) = buildAnnotatedString {
    withStyle(
        style = typography.h5.toSpanStyle()
            .copy(color = chartColors.primaryText)
    ) {
        append("$percentage")
    }
    withStyle(
        style = typography.body2.toSpanStyle()
            .copy(color = chartColors.labels)
    ) {
        append(" %")
    }
}

private fun DrawScope.drawProgressBar(
    percentage: Float,
    progressBarColor: Color,
    progressBarBackgroundColor: Color,
) {
    val sweepAngle = percentage.mapValueToDifferentRange(0f, 100f, 0f, 180f)
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
        sweepAngle = (100f - percentage).mapValueToDifferentRange(
            0f,
            100f,
            0f,
            180f
        ) - GAP_DEGREE,
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
private fun DrawScope.drawScale(color: Color, center: Offset) {
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
