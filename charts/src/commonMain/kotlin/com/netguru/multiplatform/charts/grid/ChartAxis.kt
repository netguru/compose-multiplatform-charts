package com.netguru.multiplatform.charts.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.line.XAxisConfig
import kotlin.math.roundToInt

data class YAxisTitleData(
    val labelLayout: @Composable () -> Unit,
    val labelPosition: LabelPosition = LabelPosition.Left,
) {
    enum class LabelPosition {
        Top,
        Left,
        Right,
    }
}

@Composable
internal fun YAxisLabels(
    horizontalGridLines: List<LineParameters>,
    yAxisMarkerLayout: @Composable (value: Number) -> Unit,
    yAxisTitleData: YAxisTitleData?,
    modifier: Modifier,
) {
    val markersLayout: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .fillMaxHeight()
        ) {
            horizontalGridLines.forEach { horizontalLine ->
                Box(
                    modifier = Modifier
                        .alignCenterToOffsetVertical(horizontalLine.position)
                ) {
                    yAxisMarkerLayout(horizontalLine.value)
                }
            }
        }
    }

    val labelLayout: @Composable (() -> Unit)? = yAxisTitleData?.let {
        {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                yAxisTitleData.labelLayout()
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxHeight()
    ) {
        if (yAxisTitleData?.labelPosition == YAxisTitleData.LabelPosition.Left) {
            labelLayout!!()
        }
        markersLayout()
        if (yAxisTitleData?.labelPosition == YAxisTitleData.LabelPosition.Right) {
            labelLayout!!()
        }
    }
}

private fun Modifier.alignCenterToOffsetVertical(
    offsetToAlignWith: Float,
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val placeableY = (offsetToAlignWith - (placeable.height / 2f)).coerceAtLeast(0f)

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, placeableY.roundToInt())
    }
}

internal fun Modifier.alignCenterToOffsetHorizontal(
    offsetToAlignWith: Float,
    rightEdgeOfLeftElement: Float,
    updateRightEdge: (newValue: Float) -> Unit,
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val placeableX = offsetToAlignWith - (placeable.width / 2f)

    if (placeableX > rightEdgeOfLeftElement) {
        updateRightEdge(placeableX.roundToInt().toFloat() + placeable.width)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(placeableX.roundToInt(), 0)
        }
    } else {
        layout(0, 0) {}
    }
}

@Composable
internal fun DrawXAxisMarkers(
    lineParams: List<LineParameters>,
    xAxisConfig: XAxisConfig,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Layout(
            modifier = Modifier,
            content = {
                lineParams.forEach {
                    xAxisConfig.markerLayout(it.value.toLong())
                }
            },
        ) { measurables, constraints ->
            val placeables = measurables.map {
                it.measure(constraints)
            }

            layout(width = constraints.maxWidth, height = placeables.maxOfOrNull { it.height } ?: 0) {
                var leftEdge = 0
                var rightEdge = 0

                val placeablesLeftToPlace = if (placeables.size > 1) {
                    placeables.last().let {
                        val xPos =
                            lineParams.last().position.roundToInt() - if (xAxisConfig.alignFirstAndLastToChartEdges) {
                                it.width
                            } else {
                                it.width / 2
                            }
                        rightEdge = xPos

                        it.placeRelative(
                            x = xPos,
                            y = 0,
                        )
                    }

                    placeables.subList(
                        fromIndex = 0,
                        toIndex = placeables.lastIndex,
                    )
                } else {
                    placeables
                }

                placeablesLeftToPlace.forEachIndexed { index, placeable ->
                    val xPos =
                        lineParams[index].position.roundToInt() - if (index == 0 && xAxisConfig.alignFirstAndLastToChartEdges) {
                            0
                        } else {
                            (placeable.width / 2)
                        }
                    val xPosEnd = xPos + placeable.width
                    if (!xAxisConfig.hideMarkersWhenOverlapping || (xPos > leftEdge && xPosEnd < rightEdge) || index == 0) {
                        placeable.placeRelative(
                            x = xPos,
                            y = 0,
                        )
                        leftEdge = xPos + placeable.width
                    }
                }
            }
        }
    }
}
