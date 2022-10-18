package com.netguru.multiplatform.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val TOUCH_OFFSET = 20.dp
private val OVERLAY_WIDTH = 200.dp

@Composable
internal fun OverlayInformation(
    positionX: Float,
    containerSize: Size,
    surfaceColor: Color,
    touchOffset: Dp = TOUCH_OFFSET,
    overlayWidth: Dp = OVERLAY_WIDTH,
    content: @Composable () -> Unit,
) {
    if (positionX < 0) return
    BoxWithConstraints(
        modifier = Modifier
            .offset(
                x = with(LocalDensity.current) {
                    positionX.toDp() +
                        // change offset based on cursor position to avoid out of screen drawing on the right
                        if (positionX.toDp() > (containerSize.width / 2).toDp()) {
                            -OVERLAY_WIDTH - TOUCH_OFFSET
                        } else {
                            TOUCH_OFFSET
                        }
                },
                y = touchOffset
            )
            .width(OVERLAY_WIDTH)
            .alpha(0.9f)
            .clip(RoundedCornerShape(10.dp))
            .background(surfaceColor)
            .padding(8.dp)
    ) {
        content()
    }
}
