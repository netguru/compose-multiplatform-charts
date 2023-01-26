package com.netguru.multiplatform.charts.dial

import androidx.compose.ui.unit.Dp
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * The customization parameters for [Dial]
 *
 * @param thickness The width of arc
 * The maximum value is 20% width of the chart
 * @param joinStyle How the lib should draw space between major arc and minor arc [DialJoinStyle]
 */
data class DialConfig(
    val thickness: Dp = DialDefaults.THICKNESS,
    val joinStyle: DialJoinStyle = DialDefaults.JOIN_STYLE,
    val roundCorners: Boolean = false,
    val fullAngleInDegrees: Float = 180f,
) {
    val minAngle = 90 - (fullAngleInDegrees / 2)
    val maxAngle = minAngle + fullAngleInDegrees

    val startAngle = minAngle + 180f
    val endAngle = maxAngle + 180f

    val aspectRatio: Float = run {
        // diameter is equal to 1f since we are calculating aspect ratio
        // formulas can be found here: https://www.mathopenref.com/sagitta.html
        val arcLength = (fullAngleInDegrees / 360f) * Math.PI.toFloat()
        val halfChordLength = 0.5f * sin(arcLength)
        val sagitta = if (fullAngleInDegrees <= 180f) {
            0.5f - sqrt(0.25f - halfChordLength.pow(2))
        } else {
            0.5f + sqrt(0.25f - halfChordLength.pow(2))
        }

        1f / sagitta
    }
}

sealed class DialJoinStyle {
    object Joined : DialJoinStyle()
    object Overlapped : DialJoinStyle()
    data class WithDegreeGap(val degrees: Float) : DialJoinStyle()
}
