package com.netguru.multiplatform.charts.dial.scale

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.IntOffset
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin


internal fun DrawScope.drawScale(
    color: Color,
    scaleConfig: ScaleConfig,
    calculatedAngles: List<ScalePositions.ScaleItem>,
) {
    when (scaleConfig.markType) {
        MarkType.Line -> {
            for (angle in calculatedAngles as List<ScalePositions.ScaleItem.Line>) {
                drawLine(
                    color = color,
                    start = angle.startOffset,
                    end = angle.endOffset,
                    strokeWidth = scaleConfig.scaleLineWidth.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        MarkType.Dot -> {
            calculatedAngles as List<ScalePositions.ScaleItem.Dot>
            drawPoints(
                points = calculatedAngles.map { it.offset },
                pointMode = PointMode.Points,
                color = color,
                cap = StrokeCap.Round,
                strokeWidth = scaleConfig.scaleLineLength.toPx(),
            )
        }
    }
}

@Composable
fun drawScaleLabels(
    scaleConfig: ScaleConfig,
    provideLabels: List<ScalePositions.ScaleItem>,
) {
    provideLabels
        .filter { it.showLabel }
        .forEach { scaleItem ->
            Box(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        val topLeft = when (scaleItem) {
                            is ScalePositions.ScaleItem.Dot -> scaleItem.offset
                            is ScalePositions.ScaleItem.Line -> scaleItem.startOffset
                        } - Offset(
                            x = cos(scaleItem.angle / 2).pow(2f) * placeable.width,
                            y = ((sin(scaleItem.angle) / 2) + (1 / 2f)) * placeable.height,
                        )

                        layout(placeable.width, placeable.height) {
                            placeable.place(IntOffset(topLeft.x.roundToInt(), topLeft.y.roundToInt()))
                        }
                    }
            ) {
                if (scaleItem.showLabel && scaleConfig.scaleLabelLayout != null) {
                    scaleConfig.scaleLabelLayout.invoke(scaleItem.value)
                }
            }
        }
}
