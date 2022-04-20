package com.netguru.charts.bubblechart

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.netguru.charts.mapValueToDifferentRange
import com.netguru.charts.round
import com.netguru.charts.theme.ChartDefaults
import kotlin.math.min
import kotlin.random.Random

private const val MINIMUM_BUBBLE_RADIUS = 40f

@Composable
fun BubbleChart(
    bubbles: List<Bubble>,
    unit: String,
    modifier: Modifier = Modifier.size(300.dp),
    distanceBetweenCircles: Float = -10f,
    textColor: Color = ChartDefaults.chartColors().bubbleText,
    typography: Typography = MaterialTheme.typography,
) {
    if (bubbles.isNotEmpty()) {
        BoxWithConstraints(modifier = modifier) {
            val size = min(maxWidth.value, maxHeight.value)
            val bubblePacking = remember(bubbles, size, distanceBetweenCircles) {
                val largestRadius = bubbles.maxOf { it.radius }
                val smallestRadius = bubbles.minOf { it.radius }
                BubblePacking(
                    data = bubbles.map {
                        it.withRadiusRelativeTo(
                            smallestRadius = smallestRadius,
                            largestRadius = largestRadius,
                            minRadiusPossible = MINIMUM_BUBBLE_RADIUS,
                            maxRadiusPossible = size / 4f
                        )
                    },
                    containerSize = Size(size, size),
                    distanceBetweenBubbles = distanceBetweenCircles
                )
            }

            Box(
                Modifier
                    .size(size.dp)
                    .align(alignment = Alignment.Center)
            ) {
                bubblePacking.pack().forEach { circle ->
                    BubbleComp(
                        bubble = circle,
                        unit = unit,
                        textColor = textColor,
                        typography = typography,
                    )
                }
            }
        }
    }
}

private fun Bubble.withRadiusRelativeTo(
    smallestRadius: Float,
    largestRadius: Float,
    minRadiusPossible: Float,
    maxRadiusPossible: Float,
): Bubble {
    return copy(
        radius = this.radius.mapValueToDifferentRange(
            smallestRadius,
            largestRadius,
            minRadiusPossible,
            maxRadiusPossible
        )
    )
}

@Composable
private fun BubbleComp(
    bubble: Bubble,
    unit: String,
    textColor: Color,
    typography: Typography,
) {
    Box(
        modifier = Modifier
            .size(bubble.radius.dp * 2)
            .offset((bubble.position.x - bubble.radius).dp, (bubble.position.y - bubble.radius).dp)
            .drawBehind {
                drawCircle(bubble.color)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(bubble.radius.dp * 1.6f)
        ) {
            Icon(
                painter = rememberVectorPainter(bubble.icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = textColor
            )

            Spacer(Modifier.height(4.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = formattedBubbleValue(bubble.value.round(1), unit, typography),
                color = textColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = bubble.name,
                color = textColor.copy(alpha = 0.6f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = typography.body2
            )
        }
    }
}

@Composable
fun BubbleChartPreview() {
    val data = bubbleChartSampleData()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BubbleChart(
            bubbles = data,
            unit = "kWh",
            modifier = Modifier.border(1.dp, Color.Black)
        )
    }
}

@Composable
fun bubbleChartSampleData(): List<Bubble> {
    val bubbles = mutableListOf<Bubble>()
    for (i in 0 until 4) {
        val value = 100f * Random.nextFloat()
        bubbles.add(
            Bubble(
                color = Color(
                    red = Random.nextDouble(0.0, 0.5).toFloat(),
                    green = Random.nextDouble(0.0, 0.5).toFloat(),
                    blue = 1f,
                    alpha = 0.5f
                ),
                icon = Icons.Filled.WbSunny,
                value = value,
                name = listOf("Solar", "Grid", "Fossil", "Battery").random()
            )
        )
    }
    bubbles.sortByDescending { it.radius }
    return bubbles
}

@Composable
private fun formattedBubbleValue(
    value: String,
    unit: String,
    typography: Typography,
): AnnotatedString =
    buildAnnotatedString {
        withStyle(
            style = typography.h5.toSpanStyle()
        ) {
            append(value)
        }
        withStyle(
            style = typography.overline.toSpanStyle()
        ) {
            append(" $unit")
        }
    }
