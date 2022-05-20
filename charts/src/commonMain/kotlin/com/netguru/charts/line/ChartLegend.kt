package com.netguru.charts.line

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
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
import com.netguru.charts.ChartAnimation
import com.netguru.charts.gridchart.GridDefaults

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChartLegend(
    legendData: List<LegendItemData>,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    legendItemLabel: @Composable (String) -> Unit = GridDefaults.LegendItemLabel,
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
                animation = animation,
                legendItemLabel = legendItemLabel,
            )
        }
    }
}

@Composable
private fun LegendItem(
    data: LegendItemData,
    index: Int,
    animation: ChartAnimation,
    legendItemLabel: @Composable (String) -> Unit,
) {
    var animationPlayed by remember(animation) {
        mutableStateOf(animation is ChartAnimation.Disabled)
    }

    LaunchedEffect(key1 = true) {
        animationPlayed = true // to play animation only once
    }

    val alpha = when (animation) {
        ChartAnimation.Disabled -> 1f
        is ChartAnimation.Simple -> animateFloatAsState(
            targetValue = if (animationPlayed) 1f else 0f,
            animationSpec = animation.animationSpec(),
        ).value
        is ChartAnimation.Sequenced -> animateFloatAsState(
            targetValue = if (animationPlayed) 1f else 0f,
            animationSpec = animation.animationSpec(index),
        ).value
    }

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
        legendItemLabel(data.name)
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
