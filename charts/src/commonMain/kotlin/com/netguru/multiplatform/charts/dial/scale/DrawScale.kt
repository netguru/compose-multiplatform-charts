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
import com.netguru.multiplatform.charts.dial.DialConfig
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin


internal fun DrawScope.drawScale(
    color: Color,
    config: DialConfig,
    markType: MarkType,
    calculatedAngles: List<ScalePositions.ScaleItem>,
) {
    when (markType) {
        MarkType.Line -> {
            for (angle in calculatedAngles as List<ScalePositions.ScaleItem.Line>) {
                drawLine(
                    color = color,
                    start = angle.startOffset,
                    end = angle.endOffset,
                    strokeWidth = config.scaleLineWidth.toPx(),
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
                strokeWidth = config.scaleLineLength.toPx(),
            )
        }
    }
}

@Composable
fun drawScaleLabels(
    scale: Scale,
    provideLabels: List<ScalePositions.ScaleItem>,
) {
    provideLabels.forEachIndexed { index, it ->
        Box(
            modifier = Modifier
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)

                    val topLeft = when (it) {
                        is ScalePositions.ScaleItem.Dot -> it.offset
                        is ScalePositions.ScaleItem.Line -> it.startOffset
                    } - Offset(
                        x = cos(it.angle / 2).pow(2f) * (placeable.width),
                        y = ((sin(it.angle) / 2) + (1 / 2f)) * placeable.height,
                    )

                    layout(placeable.width, placeable.height) {
                        placeable.place(IntOffset(topLeft.x.roundToInt(), topLeft.y.roundToInt()))
                    }
                }
        ) {
            when (scale) {
                is Scale.Linear -> scale.scaleLabel?.invoke("")
                is Scale.NonLinear -> scale.scaleLabel?.invoke(scale.scalePoints[index].label)
            }
        }
    }
}
