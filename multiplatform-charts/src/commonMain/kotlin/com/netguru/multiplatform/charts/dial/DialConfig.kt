package com.netguru.multiplatform.charts.dial

import androidx.compose.ui.unit.Dp

/**
 * The customization parameters for [Dial]
 *
 * @param thickness The width of arc
 * @param scalePadding Size of space between arc and scale
 * @param scaleLineWidth Thickness of scale lines
 * @param scaleLineLength The length of majors scale lines.
 * The maximum value is 20% width of the chart
 * @param joinStyle How the lib should draw space between major arc and minor arc [DialJoinStyle]
 */
data class DialConfig(
    val thickness: Dp = DialDefaults.THICKNESS,
    val scalePadding: Dp = DialDefaults.SCALE_PADDING,
    val scaleLineWidth: Dp = DialDefaults.SCALE_STROKE_WIDTH,
    val scaleLineLength: Dp = DialDefaults.SCALE_STROKE_LENGTH,
    val joinStyle: DialJoinStyle = DialDefaults.JOIN_STYLE,
    val displayScale: Boolean = true,
    val roundCorners: Boolean = false,
)

sealed class DialJoinStyle {
    object Joined : DialJoinStyle()
    object Overlapped : DialJoinStyle()
    data class WithDegreeGap(val degrees: Float) : DialJoinStyle()
}
