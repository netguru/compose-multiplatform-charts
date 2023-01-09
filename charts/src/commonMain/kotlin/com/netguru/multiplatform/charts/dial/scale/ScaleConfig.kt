package com.netguru.multiplatform.charts.dial.scale

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import com.netguru.multiplatform.charts.dial.DialConfig
import com.netguru.multiplatform.charts.dial.DialDefaults
import com.netguru.multiplatform.charts.line.Progression
import com.netguru.multiplatform.charts.mapValueToDifferentRange
import com.netguru.multiplatform.charts.toRadians
import kotlin.math.cos
import kotlin.math.sin

/**
 * Configuration for the scale
 *
 * @param scalePadding Size of space between arc and scale
 * @param scaleLineWidth Thickness of scale lines
 * @param scaleLineLength The length of majors scale lines.
 */
sealed class ScaleConfig(
    val scalePadding: Dp,
    val scaleLineWidth: Dp,
    val scaleLineLength: Dp,
    val scaleLabelLayout: (@Composable (Float) -> Unit)?,
    val markType: MarkType,
) {
    /**
     * @param smallMarkStep How often small scale marker should be shown. Value represents diff between actual dial chart
     * values. Null means it won't be shown
     * @param bigMarkStep How often big scale marker should be shown. Value represents diff between actual dial chart
     * values. Null means it won't be shown
     */
    class LinearProgressionConfig(
        scalePadding: Dp = DialDefaults.SCALE_PADDING,
        scaleLineWidth: Dp = DialDefaults.SCALE_STROKE_WIDTH,
        scaleLineLength: Dp = DialDefaults.SCALE_STROKE_LENGTH,
        scaleLabelLayout: (@Composable (Float) -> Unit)? = null,
        markType: MarkType = MarkType.Line,
        val smallMarkStep: Float? = 2f,
        val bigMarkStep: Float? = 10f,
    ) : ScaleConfig(scalePadding, scaleLineWidth, scaleLineLength, scaleLabelLayout, markType)

    class NonLinearProgressionConfig(
        scalePadding: Dp = DialDefaults.SCALE_PADDING,
        scaleLineWidth: Dp = DialDefaults.SCALE_STROKE_WIDTH,
        scaleLineLength: Dp = DialDefaults.SCALE_STROKE_LENGTH,
        scaleLabelLayout: (@Composable (Float) -> Unit)? = null,
        markType: MarkType = MarkType.Line,
    ) : ScaleConfig(scalePadding, scaleLineWidth, scaleLineLength, scaleLabelLayout, markType) {
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

    fun calculateProgressionMarkersPositions(
        config: DialConfig,
        progression: Progression,
        density: Density,
        width: Float,
        center: Offset,
        minValue: Float,
        maxValue: Float,
    ): List<ScalePositions.ScaleItem> {
        val scaleLineLength =
            with(density) { (scaleLineLength.toPx() / center.x).coerceAtMost(MAX_LINE_LENGTH) } // todo remove this constant?
        val scalePadding = with(density) { (config.thickness.toPx() + scalePadding.toPx()) / center.x }
        val startRadiusFactor = 1 - scalePadding - scaleLineLength
        val endRadiusFactor = startRadiusFactor + scaleLineLength
        val smallLineRadiusFactor = scaleLineLength * MINOR_SCALE_LENGTH_FACTOR // todo remove this constant?
        val scaleMultiplier = width / 2f

        return when (this) {
            is LinearProgressionConfig -> {
                fun position(
                    angle: Float,
                    scaleMultiplier: Float,
                    radiusFactor: Float,
                    minorRadiusFactor: Float,
                    isBigMarker: Boolean,
                ): Offset {
                    val pointRadiusFactor =
                        if (isBigMarker) {
                            radiusFactor
                        } else {
                            radiusFactor + minorRadiusFactor
                        }
                    val scaledRadius = scaleMultiplier * pointRadiusFactor
                    return Offset(cos(angle) * scaledRadius, sin(angle) * scaledRadius)
                }

                val itemsList = mutableListOf<ScalePositions.ScaleItem>()

                // big markers
                if (bigMarkStep != null) {
                    var point = minValue
                    while (point <= maxValue) {
                        val angle = point.mapValueToDifferentRange(
                            minValue,
                            maxValue,
                            config.startAngle,
                            config.endAngle,
                        ).toRadians()
                        val startPos = position(
                            angle,
                            scaleMultiplier,
                            startRadiusFactor,
                            smallLineRadiusFactor,
                            true,
                        )
                        val endPos = position(
                            angle,
                            scaleMultiplier,
                            endRadiusFactor,
                            -smallLineRadiusFactor,
                            true,
                        )
                        when (markType) {
                            MarkType.Line -> {
                                ScalePositions.ScaleItem.Line(
                                    angle = angle,
                                    showLabel = true,
                                    value = point,
                                    startOffset = center + startPos,
                                    endOffset = center + endPos,
                                )
                            }

                            MarkType.Dot -> {
                                ScalePositions.ScaleItem.Dot(
                                    angle = angle,
                                    showLabel = true,
                                    value = point,
                                    offset = center + endPos
                                )
                            }
                        }.let {
                            itemsList.add(it)
                        }

                        point += bigMarkStep
                    }
                }

                // small markers
                if (smallMarkStep != null) {
                    var point = minValue
                    while (point <= maxValue) {
                        val angle = point.mapValueToDifferentRange(
                            minValue,
                            maxValue,
                            config.startAngle,
                            config.endAngle,
                        ).toRadians()

                        if (itemsList.firstOrNull { it.angle == angle } == null) {
                            val startPos = position(
                                angle,
                                scaleMultiplier,
                                startRadiusFactor,
                                smallLineRadiusFactor,
                                false,
                            )
                            val endPos = position(
                                angle,
                                scaleMultiplier,
                                endRadiusFactor,
                                -smallLineRadiusFactor,
                                false,
                            )
                            when (markType) {
                                MarkType.Line -> {
                                    ScalePositions.ScaleItem.Line(
                                        angle = angle,
                                        showLabel = false,
                                        value = point,
                                        startOffset = center + startPos,
                                        endOffset = center + endPos,
                                    )
                                }

                                MarkType.Dot -> {
                                    ScalePositions.ScaleItem.Dot(
                                        angle = angle,
                                        showLabel = false,
                                        value = point,
                                        offset = center + endPos
                                    )
                                }
                            }.let {
                                itemsList.add(it)
                            }
                        }

                        point += smallMarkStep
                    }
                }
                itemsList
            }

            is NonLinearProgressionConfig -> {
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

                (progression as Progression.NonLinear).anchorPoints.map { point ->
                    val angle = point.position.mapValueToDifferentRange(
                        0f,
                        1f,
                        config.startAngle,
                        config.endAngle,
                    ).toRadians()
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
                                showLabel = true,
                                value = point.value,
                                startOffset = center + startPos,
                                endOffset = center + endPos,
                            )
                        }

                        MarkType.Dot -> {
                            ScalePositions.ScaleItem.Dot(
                                angle = angle,
                                showLabel = true,
                                value = point.value,
                                offset = center + endPos
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val MAX_LINE_LENGTH = 0.20f
        const val MINOR_SCALE_LENGTH_FACTOR = 0.35f
    }
}
