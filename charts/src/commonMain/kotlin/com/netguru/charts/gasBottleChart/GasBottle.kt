package com.netguru.charts.gasBottleChart

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.netguru.charts.mapValueToDifferentRange
import com.netguru.charts.theme.ChartDefaults

@Composable
fun GasBottle(
    modifier: Modifier = Modifier,
    percentage: Int = 50,
    animate: Boolean = true,
    emptyColor: Color = ChartDefaults.chartColors().emptyGasBottle,
    fullColor: Color = ChartDefaults.chartColors().fullGasBottle,
) {
    var animationPlayed by remember(animate) {
        mutableStateOf(!animate)
    }
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    val targetProgress by animateIntAsState(
        targetValue = if (animationPlayed) percentage else 0,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    val gasTank = rememberVectorPainter(image = GasTank)
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .graphicsLayer(alpha = 0.99f)
                .alpha(0.8f)
                .aspectRatio(0.59f)
                .drawBehind {
                    drawGasProgressBar(
                        percentage = targetProgress,
                        gasTank = gasTank,
                        fullColor = fullColor,
                        emptyColor = emptyColor
                    )
                }
        )
    }
}

private fun DrawScope.drawGasProgressBar(
    gasTank: VectorPainter,
    percentage: Int,
    fullColor: Color,
    emptyColor: Color,
) {
    var percentageTemp = percentage.coerceIn(0, 100)
    if (percentage in 1..5) percentageTemp = 5

    val fluidTopLevel = size.height * 0.3f
    val fluidBottomLevel = size.height * 0.88f
    val fluidTopSurfaceHeight = size.height * 0.09f

    val fillLevel =
        percentageTemp.toFloat().mapValueToDifferentRange(0f, 100f, fluidBottomLevel, fluidTopLevel)
    val color = getColorFromPercentage(
        color1 = fullColor,
        color2 = emptyColor,
        percentage = percentageTemp.toFloat()
    )

    // gas bottle
    with(gasTank) {
        draw(size)
    }

    if (percentageTemp > 0) {
        // fluid fill
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    color.copy(alpha = 0.8f),
                    Color.Transparent
                ),
                startY = fillLevel,
                endY = fluidBottomLevel
            ),
            topLeft = Offset(0f, fillLevel),
            size = Size(size.width, fluidBottomLevel - fillLevel),
            blendMode = BlendMode.SrcAtop
        )

        // ellipses, fluid top surface
        drawOval(
            topLeft = Offset(0f, fillLevel - fluidTopSurfaceHeight / 2f),
            color = Color.White,
            size = Size(size.width, fluidTopSurfaceHeight),
            style = Stroke(width = 2f),
            alpha = 0.7f,
            blendMode = BlendMode.SrcAtop
        )
        drawOval(
            topLeft = Offset(0f, fillLevel - fluidTopSurfaceHeight / 2f),
            color = color,
            size = Size(size.width, fluidTopSurfaceHeight),
            alpha = 1f,
            blendMode = BlendMode.SrcAtop
        )
    }
}

// crude interpolation between colors
fun getColorFromPercentage(
    color1: Color,
    color2: Color,
    percentage: Float,
): Color {
    val fraction = percentage / 100f
    val inverseFraction = 1.0 - fraction
    val redPart = (255 * (color1.red * fraction + color2.red * inverseFraction)).toInt()
    val greenPart = (255 * (color1.green * fraction + color2.green * inverseFraction)).toInt()
    val bluePart = (255 * (color1.blue * fraction + color2.blue * inverseFraction)).toInt()
    return Color(red = redPart, green = greenPart, blue = bluePart)
}
