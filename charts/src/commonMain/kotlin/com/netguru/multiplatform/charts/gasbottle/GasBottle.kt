package com.netguru.multiplatform.charts.gasbottle

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
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
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.getAnimationAlphas
import com.netguru.multiplatform.charts.mapValueToDifferentRange
import com.netguru.multiplatform.charts.theme.ChartColors
import com.netguru.multiplatform.charts.theme.ChartTheme

/**
 * Chart in the shape of a gas cylinder. Only shows percentage from 0 to 100 and without any labels.
 *
 * The actual numeric value is never shown. It only colors the cylinder based on the value. Color of
 * the colored part is interpolated between [ChartColors.fullGasBottle] and
 * [ChartColors.emptyGasBottle] based on the 'fullness' of the cylinder.
 *
 * @param percentage Value to portray
 * @param animation Animation to use. [ChartDisplayAnimation.Sequenced] throws an
 * [kotlin.UnsupportedOperationException], since there is only one value to display.
 * @param colors Allows to specify full and empty bottle color
 *
 * @throws kotlin.UnsupportedOperationException when [ChartDisplayAnimation.Sequenced] is used
 */
@Composable
fun GasBottle(
    percentage: Float,
    modifier: Modifier = Modifier,
    animation: ChartDisplayAnimation = ChartDisplayAnimation.Simple(),
    colors: GasBottleColors = ChartTheme.colors.gasBottleColors,
) {
    val animationPercentage = getAnimationAlphas(
        animation = animation,
        numberOfElementsToAnimate = 1,
        uniqueDatasetKey = percentage,
    ).first()

    val targetProgress = percentage * animationPercentage

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
                        fullColor = colors.fullGasBottle,
                        emptyColor = colors.emptyGasBottle,
                    )
                }
        )
    }
}

private fun DrawScope.drawGasProgressBar(
    gasTank: VectorPainter,
    percentage: Float,
    fullColor: Color,
    emptyColor: Color,
) {
    var percentageTemp = percentage.coerceIn(0f, 100f)
    if (percentage in 1.0..5.0) percentageTemp = 5f

    val fluidTopLevel = size.height * 0.3f
    val fluidBottomLevel = size.height * 0.88f
    val fluidTopSurfaceHeight = size.height * 0.09f

    val fillLevel =
        percentageTemp.mapValueToDifferentRange(0f, 100f, fluidBottomLevel, fluidTopLevel)
    val color = getColorFromPercentage(
        color1 = fullColor,
        color2 = emptyColor,
        percentage = percentageTemp
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
private fun getColorFromPercentage(
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
