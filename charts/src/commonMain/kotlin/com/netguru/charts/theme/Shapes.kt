package com.netguru.charts.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape

internal val LocalShapes = staticCompositionLocalOf {
    ChartsShapes(
        cornersRounded = RoundedCornerShape(ZeroCornerSize)
    )
}

@Immutable
data class ChartsShapes(
    val cornersRounded: Shape
)
