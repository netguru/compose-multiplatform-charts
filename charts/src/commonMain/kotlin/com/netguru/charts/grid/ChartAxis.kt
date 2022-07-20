package com.netguru.charts.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
internal fun YAxisLabels(
    horizontalGridLines: List<LineParameters>,
    yAxisMarkerLayout: @Composable (value: Number) -> Unit,
) {
    Box(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .fillMaxHeight()
            .padding(end = 8.dp)
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
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val placeableX = offsetToAlignWith - (placeable.width / 2f)

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(placeableX.roundToInt(), 0)
    }
}
