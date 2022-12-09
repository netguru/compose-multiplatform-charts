package com.netguru.multiplatform.charts.line

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.ChartAnimation
import com.netguru.multiplatform.charts.bar.BarChartConfig
import com.netguru.multiplatform.charts.getAnimationAlphas
import com.netguru.multiplatform.charts.grid.GridDefaults

@Composable
fun ChartLegend(
    legendData: List<LegendItemData>,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    config: BarChartConfig = BarChartConfig(),
    legendItemLabel: @Composable (name: String, unit: String?) -> Unit = GridDefaults.LegendItemLabel,
    columnMinWidth: Dp = 200.dp,
) {
    val alpha = getAnimationAlphas(
        animation = animation,
        numberOfElementsToAnimate = legendData.size,
        uniqueDatasetKey = legendData,
    )

    BoxWithConstraints(
        modifier.padding(16.dp)
    ) {
        val cols = maxOf((maxWidth / columnMinWidth).toInt(), 1)
        val rows = legendData.chunked(cols)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(rows) { rowIndex, legendRowItems ->
                Row(
                    Modifier.height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    legendRowItems.indices.forEach { colIndex ->
                        val index = (rowIndex * cols) + colIndex

                        LegendItem(
                            data = legendData[index],
                            alpha = alpha[index],
                            legendItemLabel = legendItemLabel,
                            config = config,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LegendItem(
    data: LegendItemData,
    alpha: Float,
    config: BarChartConfig,
    legendItemLabel: @Composable (name: String, unit: String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .alpha(alpha)
    ) {
        Box(
            modifier = Modifier
                .size(data.selectSymbolSize())
                .drawBehind {
                    when (data.symbolShape) {
                        SymbolShape.LINE ->
                            drawLine(
                                strokeWidth = size.height,
                                pathEffect = data.pathEffect,
                                color = data.color,
                                start = Offset(0f, size.height / 2),
                                end = Offset(size.width, size.height / 2)
                            )

                        SymbolShape.RECTANGLE ->
                            drawRoundRect(
                                color = data.color,
                                cornerRadius = CornerRadius(
                                    config.cornerRadius.toPx(),
                                    config.cornerRadius.toPx(),
                                )
                            )
                    }
                }
        )

        Spacer(modifier = Modifier.width(8.dp))
        legendItemLabel(data.name, data.unit)
    }
}

@Immutable
data class LegendItemData(
    val name: String,
    val unit: String?,
    val symbolShape: SymbolShape,
    val color: Color,
    val pathEffect: PathEffect?,
)

@Composable
private fun LegendItemData.selectSymbolSize() = when (symbolShape) {
    SymbolShape.LINE -> DpSize(width = 32.dp, height = 4.dp)
    SymbolShape.RECTANGLE -> DpSize(width = 12.dp, height = 12.dp)
}

enum class SymbolShape {
    LINE,
    RECTANGLE
}
