package com.netguru.multiplatform.charts.bubble

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.StartAnimation
import com.netguru.multiplatform.charts.bubble.BubbleDefaults.MINIMUM_BUBBLE_RADIUS
import com.netguru.multiplatform.charts.mapValueToDifferentRange
import kotlin.math.min
import kotlin.random.Random

/**
 * Represents the data in a form of bubbles that can overlap and are sized based on data value
 * ratios. The position of bubbles is not stable and will likely change every time it is loaded!
 *
 * @param bubbles Data to represent
 * @param animation Animation to use for displaying the data
 * @param distanceBetweenCircles Distance between circles in pixels. If negative, bubbles will
 * overlap.
 * @param bubbleLabel Composable to show in the middle of the bubble.
 */
@Composable
fun BubbleChart(
    bubbles: List<Bubble>,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    distanceBetweenCircles: Float = -10f,
    bubbleLabel: @Composable (Bubble) -> Unit = BubbleDefaults.BubbleLabel,
) {
    if (bubbles.isEmpty()) {
        return
    }

    val animationPlayed = StartAnimation(animation, bubbles)
    val animatedScale = when (animation) {
        ChartAnimation.Disabled -> bubbles.indices.map { 1f }
        is ChartAnimation.Simple -> bubbles.indices.map {
            animateFloatAsState(
                targetValue = if (animationPlayed) 1f else 0f,
                animationSpec = animation.animationSpec()
            ).value
        }
        is ChartAnimation.Sequenced -> bubbles.indices.map {
            animateFloatAsState(
                targetValue = if (animationPlayed) 1f else 0f,
                animationSpec = animation.animationSpec(it)
            ).value
        }
    }

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
            bubblePacking.pack().forEachIndexed { index, bubble ->
                BubbleComp(
                    bubble = bubble,
                    animationScale = animatedScale[index],
                    bubbleContent = bubbleLabel,
                )
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
    animationScale: Float,
    bubbleContent: @Composable (Bubble) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(bubble.radius.dp * 2)
            .offset((bubble.position.x - bubble.radius).dp, (bubble.position.y - bubble.radius).dp)
            .drawBehind {
                drawCircle(bubble.color.copy(alpha = animationScale))
            }
            .alpha(animationScale),
        contentAlignment = Alignment.Center
    ) {
        bubbleContent(bubble)
    }
}

@Composable
fun BubbleChartPreview() {
    val data = bubbleChartSampleData()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        BubbleChart(
            bubbles = data,
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
