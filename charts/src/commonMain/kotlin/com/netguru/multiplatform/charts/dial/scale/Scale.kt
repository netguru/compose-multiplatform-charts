package com.netguru.multiplatform.charts.dial.scale

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import com.netguru.multiplatform.charts.dial.DialConfig
import com.netguru.multiplatform.charts.dial.MAX_ANGLE
import com.netguru.multiplatform.charts.dial.MIN_ANGLE
import com.netguru.multiplatform.charts.mapValueToDifferentRange
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

sealed class Scale(
    val scaleLabel: (@Composable (String) -> Unit)?,
    val markType: MarkType,
) {
    class Linear(
        scaleLabel: (@Composable (String) -> Unit)? = null,
        markType: MarkType = MarkType.Line,
    ) : Scale(scaleLabel, markType)

    class NonLinear(
        val scalePoints: List<ScalePoint>,
        scaleLabel: (@Composable (String) -> Unit)? = null,
        markType: MarkType = MarkType.Line,
    ) : Scale(scaleLabel, markType) {
        /**
         * @param value Value that this point represents
         * @param position position on the scale in "percentage" in range [0, 1]
         * @param label to display next to the point
         */
        data class ScalePoint(
            val value: Float,
            val position: Float,
            val label: String,
        )
    }

    private companion object {
        const val MAX_LINE_LENGTH = 0.20f
        const val MINOR_SCALE_ALPHA = 0.5f
        const val MINOR_SCALE_LENGTH_FACTOR = 0.35f
        const val SCALE_STEP = 2
        const val MAJOR_SCALE_MODULO = 5 * SCALE_STEP
    }

    fun calculateAngles(
        config: DialConfig,
        density: Density,
        width: Float,
        center: Offset,
    ): List<ScalePositions.ScaleItem> {
        val scaleLineLength =
            with(density) { (config.scaleLineLength.toPx() / center.x).coerceAtMost(MAX_LINE_LENGTH) }
        val scalePadding = with(density) { (config.thickness.toPx() + config.scalePadding.toPx()) / center.x }
        val startRadiusFactor = 1 - scalePadding - scaleLineLength
        val endRadiusFactor = startRadiusFactor + scaleLineLength
        val smallLineRadiusFactor = scaleLineLength * MINOR_SCALE_LENGTH_FACTOR
        val scaleMultiplier = width / 2f

        return when (this) {
            is Linear -> {
                fun Int.position(
                    angle: Float,
                    scaleMultiplier: Float,
                    radiusFactor: Float,
                    minorRadiusFactor: Float,
                ): Offset {
                    val pointRadiusFactor =
                        if (this % MAJOR_SCALE_MODULO == 0)
                            radiusFactor
                        else
                            radiusFactor + minorRadiusFactor
                    val scaledRadius = scaleMultiplier * pointRadiusFactor
                    return Offset(cos(angle) * scaledRadius, sin(angle) * scaledRadius)
                }
                (0..100 step SCALE_STEP).map { point ->
                    val angle = (
                            point.toFloat()
                                .mapValueToDifferentRange(
                                    0f,
                                    100f,
                                    MIN_ANGLE + 180,
                                    MAX_ANGLE + 180,
                                )
                            ) * PI.toFloat() / 180f // to radians
                    val startPos = point.position(
                        angle,
                        scaleMultiplier,
                        startRadiusFactor,
                        smallLineRadiusFactor,
                    )
                    val endPos = point.position(
                        angle,
                        scaleMultiplier,
                        endRadiusFactor,
                        -smallLineRadiusFactor,
                    )
                    when (markType) {
                        MarkType.Line -> {
                            Triple(angle, center + startPos, center + endPos)
                            ScalePositions.ScaleItem.Line(
                                angle = angle,
                                startOffset = center + startPos,
                                endOffset = center + endPos,
                            )
                        }

                        MarkType.Dot -> {
                            ScalePositions.ScaleItem.Dot(
                                angle = angle,
                                offset = center + endPos
                            )
                        }
                    }
                }
            }

            is NonLinear -> {
                fun positionLine(
                    angle: Float,
                    scaleMultiplier: Float,
                    radiusFactor: Float,
                    minorRadiusFactor: Float,
                ): Offset {
                    val pointRadiusFactor = radiusFactor + minorRadiusFactor
                    val scaledRadius = scaleMultiplier * pointRadiusFactor
                    return Offset(cos(angle) * scaledRadius, sin(angle) * scaledRadius)
                }
                scalePoints.map { point ->
                    val angle = (
                            point.position
                                .mapValueToDifferentRange(
                                    0f,
                                    1f,
                                    MIN_ANGLE + 180,
                                    MAX_ANGLE + 180,
                                )
                            ) * PI.toFloat() / 180f // to radians
                    val startPos = positionLine(
                        angle,
                        scaleMultiplier,
                        startRadiusFactor,
                        smallLineRadiusFactor
                    )
                    val endPos = positionLine(
                        angle,
                        scaleMultiplier,
                        endRadiusFactor,
                        -smallLineRadiusFactor
                    )

                    when (markType) {
                        MarkType.Line -> {
                            Triple(angle, center + startPos, center + endPos)
                            ScalePositions.ScaleItem.Line(
                                angle = angle,
                                startOffset = center + startPos,
                                endOffset = center + endPos,
                            )
                        }

                        MarkType.Dot -> {
                            ScalePositions.ScaleItem.Dot(
                                angle = angle,
                                offset = center + endPos
                            )
                        }
                    }
                }
            }
        }
    }
}
