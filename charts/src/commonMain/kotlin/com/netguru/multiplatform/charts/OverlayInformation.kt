package com.netguru.multiplatform.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
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
    backgroundColor: Color,
    touchOffsetVertical: Dp,
    touchOffsetHorizontal: Dp,
    requiredOverlayWidth: Dp?,
    overlayAlpha: Float,
    pointsToAvoid: List<Offset> = emptyList(),
    content: @Composable () -> Unit,
) {
    if (positionX == null || positionX < 0) {
        return
    }

    val density = LocalDensity.current

    var overlayHeight by remember {
        mutableStateOf(0)
    }
    var overlayWidth by remember {
        val pxs: Int
        with(density) {
            pxs = requiredOverlayWidth?.roundToPx() ?: 0
        }
        mutableStateOf(pxs)
    }


    val putInfoOnTheLeft = positionX > (containerSize.width / 2)
    val (offsetX, offsetY) = remember(pointsToAvoid, overlayHeight, overlayWidth, putInfoOnTheLeft) {
        pointsToAvoid
            .takeIf { it.isNotEmpty() }
            ?.let {
                val x: Dp
                val y: Dp
                with(density) {
                    x = if (putInfoOnTheLeft) {
                        val minX = it.minOf { it.x }.toDp()
                        minX - touchOffsetHorizontal - overlayWidth.toDp()
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
                                -overlayWidth.toDp() - touchOffsetHorizontal
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
                if (requiredOverlayWidth == null) {
                    overlayWidth = it.width
                }
            }
            .offset(
                x = offsetX,
                y = offsetY,
            )
            .alpha(overlayAlpha)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(8.dp)
            .then(
                if (requiredOverlayWidth != null) {
                    Modifier.requiredWidth(requiredOverlayWidth)
                } else {
                    Modifier
                }
            )
    ) {
        content()
    }
}
