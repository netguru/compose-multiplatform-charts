package com.netguru.multiplatform.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun OverlayInformation(
    positionX: Float?,
    containerSize: Size,
    surfaceColor: Color,
    touchOffsetVertical: Dp,
    touchOffsetHorizontal: Dp,
    overlayWidth: Dp,
    pointsToAvoid: List<Offset> = emptyList(),
    content: @Composable () -> Unit,
) {
    if (positionX == null || positionX < 0) {
        return
    }

    var overlayHeight by remember {
        mutableStateOf(0)
    }

    val density = LocalDensity.current

    val putInfoOnTheLeft = positionX > (containerSize.width / 2)
    val (offsetX, offsetY) = remember(pointsToAvoid, overlayHeight, putInfoOnTheLeft) {
        pointsToAvoid
            .takeIf { it.isNotEmpty() }
            ?.let {
                val x: Dp
                val y: Dp
                with(density) {
                    x = if (putInfoOnTheLeft) {
                        val minX = it.minOf { it.x }.toDp()
                        minX - touchOffsetHorizontal - overlayWidth
                    } else {
                        val maxX = it.maxOf { it.x }.toDp()
                        maxX + touchOffsetHorizontal
                    }

                    val minY = it.minOf { it.y }
                    val maxY = it.maxOf { it.y }
                    y = (containerSize.height - ((maxY + minY) / 2) - (overlayHeight / 2))
                        .coerceIn(
                            minimumValue = 0f,
                            maximumValue = containerSize.height - overlayHeight
                        )
                        .toDp()
                }

                x to y
            }
            ?: run {
                with(density) {
                    positionX.toDp() +
                            // change offset based on cursor position to avoid out of screen drawing on the right
                            if (putInfoOnTheLeft) {
                                -overlayWidth - touchOffsetHorizontal
                            } else {
                                touchOffsetHorizontal
                            }
                } to touchOffsetVertical
            }
    }

    BoxWithConstraints(
        modifier = Modifier
            .onSizeChanged {
                overlayHeight = it.height
            }
            .offset(
                x = offsetX,
                y = offsetY,
            )
            .width(overlayWidth)
            .alpha(0.9f)
            .clip(RoundedCornerShape(10.dp))
            .background(surfaceColor)
            .padding(8.dp)
    ) {
        content()
    }
}
