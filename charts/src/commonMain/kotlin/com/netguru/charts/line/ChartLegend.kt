package com.netguru.charts.line

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

private const val DEFAULT_ANIMATION_DURATION_MS = 300
private const val DEFAULT_ANIMATION_DELAY_MS = 100
private const val ALPHA_MAX = 1f
private const val ALPHA_MIN = 0f

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChartLegend(
    legendData: List<LegendItemData>,
    textColor: Color,
    modifier: Modifier = Modifier,
    animate: Boolean = true,
    animationDuration: Int = DEFAULT_ANIMATION_DURATION_MS,
    animationDelay: Int = DEFAULT_ANIMATION_DELAY_MS,
) {
    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Adaptive(200.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
    ) {
        items(legendData.count()) { index ->
            LegendItem(
                data = legendData[index],
                index = index,
                animate = animate,
                animationDuration = animationDuration,
                animationDelay = animationDelay,
                textColor = textColor,
            )
        }
    }
}

@Composable
private fun LegendItem(
    data: LegendItemData,
    index: Int,
    animate: Boolean,
    animationDuration: Int,
    animationDelay: Int,
    textColor: Color,
) {
    var animationPlayed by remember(animate) {
        mutableStateOf(!animate)
    }

    LaunchedEffect(key1 = true) {
        animationPlayed = true // to play animation only once
    }

    val alpha by animateFloatAsState(
        targetValue = if (animationPlayed) ALPHA_MAX else ALPHA_MIN,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = index * animationDelay
        )
    )

    Row(modifier = Modifier.alpha(alpha), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(data.selectSymbolSize())
                .drawBehind {
                    drawLine(
                        strokeWidth = size.height,
                        pathEffect = if (data.dashed) dashedPathEffect else null,
                        color = data.color,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2)
                    )
                }
        )

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = data.name,
            color = textColor,
        )
    }
}

@Immutable
data class LegendItemData(
    val name: String,
    val symbolShape: SymbolShape,
    val color: Color,
    val dashed: Boolean,
)

@Composable
private fun LegendItemData.selectSymbolSize() = when (symbolShape) {
    SymbolShape.LINE -> DpSize(width = 16.dp, height = 4.dp)
    SymbolShape.RECTANGLE -> DpSize(width = 12.dp, height = 12.dp)
}

enum class SymbolShape {
    LINE,
    RECTANGLE
}
